package net.slc.ef.fla.qualif.model.restaurant;

import net.slc.ef.fla.qualif.async.ASExecutor;
import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.PersonFactory;
import net.slc.ef.fla.qualif.model.person.PersonInitialGenerator;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.chef.ChefFactory;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefDoneState;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefIdleState;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.customer.CustomerFactory;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.person.waiter.WaiterFactory;
import net.slc.ef.fla.qualif.model.person.waiter.state.WaiterIdleState;
import net.slc.ef.fla.qualif.model.restaurant.chair.Chair;
import net.slc.ef.fla.qualif.model.restaurant.chair.ChairStatus;
import net.slc.ef.fla.qualif.model.restaurant.spawner.CustomerSpawner;
import net.slc.ef.fla.qualif.model.restaurant.state.*;
import net.slc.ef.fla.qualif.model.restaurant.task.ScannerTask;
import net.slc.ef.fla.qualif.model.restaurant.task.TickerTask;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RestaurantFacade {

    private final Restaurant restaurant;
    private final PersonFactory customerFactory, waiterFactory, chefFactory;
    private final Map<Class<? extends RestaurantState>, RestaurantState> stateMap;

    public RestaurantFacade(Restaurant restaurant) {
        this.restaurant = restaurant;

        PersonInitialGenerator initialsGenerator = new PersonInitialGenerator(this.restaurant);
        this.customerFactory = new CustomerFactory(this.restaurant, initialsGenerator);
        this.waiterFactory = new WaiterFactory(this.restaurant, initialsGenerator);
        this.chefFactory = new ChefFactory(this.restaurant, initialsGenerator);

        this.stateMap = new HashMap<>();
        this.stateMap.put(RestaurantInitializationState.class, new RestaurantInitializationState(this.restaurant));
        this.stateMap.put(RestaurantRunningState.class, new RestaurantRunningState(this.restaurant));
        this.stateMap.put(RestaurantPausedState.class, new RestaurantPausedState(this.restaurant));
        this.stateMap.put(RestaurantHiringState.class, new RestaurantHiringState(this.restaurant));
        this.stateMap.put(RestaurantUpgradingState.class, new RestaurantUpgradingState(this.restaurant));
        this.stateMap.put(RestaurantUpgradeChefAState.class, new RestaurantUpgradeChefAState(this.restaurant));
        this.stateMap.put(RestaurantUpgradeWaiterState.class, new RestaurantUpgradeWaiterState(this.restaurant));
    }

    public void start() {
        restaurant.getAsExecutorManager().addExecutor(
                ASExecutor.builder()
                        .executor(Executors::newSingleThreadScheduledExecutor)
                        .task(new TickerTask(restaurant))
                        .delay(0)
                        .period(1)
                        .timeUnit(TimeUnit.SECONDS)
                        .runningCondition((ignored) -> this.restaurant.getState().isState(RestaurantRunningState.class))
                        .build()
        );
        restaurant.getAsExecutorManager().addExecutor(
                ASExecutor.builder()
                        .executor(Executors::newSingleThreadScheduledExecutor)
                        .task(new ScannerTask(restaurant))
                        .delay(0)
                        .period(1)
                        .timeUnit(TimeUnit.SECONDS)
                        .runningCondition((ignored) -> true)
                        .build()
        );

        restaurant.setMoney(1300);
        restaurant.setScore(0);
        restaurant.getChairs().addAll(List.of(
                new Chair(1),
                new Chair(2),
                new Chair(3),
                new Chair(4)
        ));

        this.restaurant.getWaiters().add((Waiter) waiterFactory.create());
        this.restaurant.getWaiters().add((Waiter) waiterFactory.create());

        this.restaurant.getChefs().add((Chef) chefFactory.create());
        this.restaurant.getChefs().add((Chef) chefFactory.create());

        this.restaurant.addObserver(new CustomerSpawner());
    }

    public CompletableFuture<Void> await() {
        return restaurant.getAsExecutorManager().awaitShutdown();
    }

    public void end() {
        restaurant.getAsExecutorManager().shutdown();
        Restaurant.destroy();
    }

    public List<AbstractPerson> getPersons() {
        List<AbstractPerson> persons = new ArrayList<>();
        persons.addAll(this.getCustomers());
        persons.addAll(this.restaurant.getWaiters());
        persons.addAll(this.restaurant.getChefs());

        return persons;
    }


    public List<Customer> getCustomers() {
        return this.restaurant.getChairs().stream()
                .filter(chair -> chair.getStatus() == ChairStatus.OCCUPIED)
                .map(Chair::getCustomer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public int getCapacity() {
        return this.restaurant.getChairs().size();
    }

    public int getRemainingCapacity() {
        int remainingCapacity = 0;
        for (Chair chair : this.restaurant.getChairs()) {
            if (chair.getStatus() == ChairStatus.AVAILABLE) {
                remainingCapacity++;
            }
        }

        return remainingCapacity;
    }

    public void addCustomer() {
        // get first empty chair
        Chair chair = this.restaurant.getChairs().stream()
                .filter(chair1 -> chair1.getStatus() == ChairStatus.AVAILABLE)
                .findFirst()
                .orElse(null);

        if (chair == null) {
            throw new IllegalStateException("No empty chair");
        }

        chair.setStatus(ChairStatus.OCCUPIED);
        chair.setCustomer((Customer) customerFactory.create());
    }

    public void removeCustomer(Customer customer) {
        this.restaurant.getChairs().stream()
                .filter(chair -> chair.getCustomer() == customer)
                .findFirst()
                .ifPresent(chair -> {
                    chair.setStatus(ChairStatus.AVAILABLE);
                    chair.setCustomer(null);
                });
    }

    public void addMoney(int money) {
        this.restaurant.setMoney(this.restaurant.getMoney() + money);
    }

    public void removeMoney(int money) {
        assert money >= 0;
        assert this.restaurant.getMoney() >= money;

        this.restaurant.setMoney(this.restaurant.getMoney() - money);
    }

    public boolean canPurchase(int price) {
        return this.restaurant.getMoney() >= price;
    }

    public void addScore(int score) {
        this.restaurant.setScore(this.restaurant.getScore() + score);
    }

    public void removeScore(int score) {
        assert score >= 0;

        this.restaurant.setScore(this.restaurant.getScore() - score);
    }

    public void switchState(Class<? extends RestaurantState> stateClass) {
        RestaurantState state = this.stateMap.get(stateClass);
        assert state != null;

        this.restaurant.setState(state);
        this.restaurant.getState().onEnter();
    }

    public int calculateSeatPrice() {
        int size = this.restaurant.getChairs().size();
        if (size >= 13) {
            return -1;
        }

        return size * 100;
    }

    public void addChair() {
        // deduct money
        int newSeatPrice = this.calculateSeatPrice();
        this.removeMoney(newSeatPrice);

        int chairNumber = this.restaurant.getChairs().size() + 1;
        this.restaurant.getChairs().add(new Chair(chairNumber));
    }

    public int calculateWaiterPrice() {
        int size = this.restaurant.getWaiters().size();
        if (size >= 7) {
            return -1;
        }

        return size * 150;
    }

    public void hireWaiter() {
        // deduct money
        int newWaiterPrice = this.calculateWaiterPrice();
        this.removeMoney(newWaiterPrice);

        this.restaurant.getWaiters().add((Waiter) waiterFactory.create());
    }

    public int calculateChefPrice() {
        int size = this.restaurant.getChefs().size();
        if (size >= 7) {
            return -1;
        }

        return size * 200;
    }

    public void hireChef() {
        // deduct money
        int newChefPrice = this.calculateChefPrice();
        this.removeMoney(newChefPrice);

        this.restaurant.getChefs().add((Chef) chefFactory.create());
    }

    public Waiter getIdlingWaiter() {
        Optional<Waiter> optionalWaiter = this.restaurant.getWaiters().stream()
                .filter(waiter -> waiter.getState().isState(WaiterIdleState.class))
                .filter(waiter -> !this.isBooked(waiter))
                .findFirst();

        if (optionalWaiter.isEmpty()) {
            return null;
        }

        Waiter waiter = optionalWaiter.get();
        this.restaurant.getBookedPersons().add(waiter);

        return waiter;
    }

    public Chef getIdlingChef() {
        Optional<Chef> optionalChef = this.restaurant.getChefs().stream()
                .filter(chef -> chef.getState().isState(ChefIdleState.class))
                .filter(chef -> !this.isBooked(chef))
                .findFirst();

        if (optionalChef.isEmpty()) {
            return null;
        }

        Chef chef = optionalChef.get();
        this.restaurant.getBookedPersons().add(chef);

        return chef;
    }

    public Chef getDoneChef() {
        Optional<Chef> optionalChef = this.restaurant.getChefs().stream()
                .filter(chef -> chef.getState().isState(ChefDoneState.class))
                .filter(chef -> !this.isBooked(chef))
                .findFirst();

        if (optionalChef.isEmpty()) {
            return null;
        }

        Chef chef = optionalChef.get();
        this.restaurant.getBookedPersons().add(chef);

        return chef;
    }

    public boolean isBooked(AbstractPerson person) {
        return this.restaurant.getBookedPersons().contains(person);
    }

    public void clearBookedPersons() {
        this.restaurant.getBookedPersons().clear();
    }

    public void selectUpgradingChef(Chef chef) {
        this.restaurant.setUpgradingChef(chef);
    }

    public Chef getUpgradingChef() {
        return this.restaurant.getUpgradingChef();
    }

    public void upgradeChefSpeed(Chef chef) {
        // deduct money
        int upgradePrice = 150;
        this.removeMoney(upgradePrice);

        chef.setSpeed(chef.getSpeed() + 1);
    }

    public void upgradeChefSkill(Chef chef) {
        // deduct money
        int upgradePrice = 150;
        this.removeMoney(upgradePrice);

        chef.setSkillLevel(chef.getSkillLevel() + 1);
    }

    public void upgradeWaiterSpeed(Waiter waiter) {
        // deduct money
        int upgradePrice = 150;
        this.removeMoney(upgradePrice);

        waiter.setSpeed(waiter.getSpeed() + 1);
    }
}
