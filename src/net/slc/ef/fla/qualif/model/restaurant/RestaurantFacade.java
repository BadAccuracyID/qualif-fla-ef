package net.slc.ef.fla.qualif.model.restaurant;

import net.slc.ef.fla.qualif.async.ASExecutor;
import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.PersonFactory;
import net.slc.ef.fla.qualif.model.person.PersonInitialGenerator;
import net.slc.ef.fla.qualif.model.person.cook.Cook;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.customer.CustomerFactory;
import net.slc.ef.fla.qualif.model.person.server.Server;
import net.slc.ef.fla.qualif.model.person.server.ServerFactory;
import net.slc.ef.fla.qualif.model.restaurant.chair.Chair;
import net.slc.ef.fla.qualif.model.restaurant.chair.ChairStatus;
import net.slc.ef.fla.qualif.model.restaurant.state.*;
import net.slc.ef.fla.qualif.model.restaurant.task.PrinterTask;
import net.slc.ef.fla.qualif.model.restaurant.task.ScannerTask;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RestaurantFacade {

    private final Restaurant restaurant;
    private final PersonFactory customerFactory, serverFactory, cookFactory;
    private final Map<Class<? extends RestaurantState>, RestaurantState> stateMap;

    public RestaurantFacade(Restaurant restaurant) {
        this.restaurant = restaurant;

        PersonInitialGenerator initialsGenerator = new PersonInitialGenerator(this.restaurant);
        this.customerFactory = new CustomerFactory(this.restaurant, initialsGenerator);
        this.serverFactory = new ServerFactory(this.restaurant, initialsGenerator);
        this.cookFactory = new ServerFactory(this.restaurant, initialsGenerator);

        this.stateMap = new HashMap<>();
        this.stateMap.put(RestaurantInitializationState.class, new RestaurantInitializationState(this.restaurant));
        this.stateMap.put(RestaurantRunningState.class, new RestaurantRunningState(this.restaurant));
        this.stateMap.put(RestaurantPausedState.class, new RestaurantPausedState(this.restaurant));
        this.stateMap.put(RestaurantHiringState.class, new RestaurantHiringState(this.restaurant));
        this.stateMap.put(RestaurantUpgradingState.class, new RestaurantUpgradingState(this.restaurant));
        this.stateMap.put(RestaurantUpgradeCookState.class, new RestaurantUpgradeCookState(this.restaurant));
        this.stateMap.put(RestaurantUpgradeWaiterState.class, new RestaurantUpgradeWaiterState(this.restaurant));
    }

    public void addExecutor(ExecutorService executor) {
        restaurant.getExecutorServices().add(executor);
    }

    public void removeExecutor(ExecutorService executor) {
        restaurant.getExecutorServices().remove(executor);
    }

    public void start() {
        restaurant.getAsExecutorManager().addExecutor(
                ASExecutor.builder()
                        .executor(Executors::newSingleThreadScheduledExecutor)
                        .task(new PrinterTask(restaurant))
                        .delay(0)
                        .period(1)
                        .timeUnit(TimeUnit.SECONDS)
                        .runningCondition((ignored) -> this.isState(RestaurantRunningState.class))
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
    }

    public void end() {
        restaurant.getAsExecutorManager().shutdown();
        Restaurant.destroy();
    }

    public List<AbstractPerson> getPersons() {
        List<AbstractPerson> persons = new ArrayList<>();
        persons.addAll(this.getCustomers());
        persons.addAll(this.restaurant.getServers());

        return persons;
    }


    public List<Customer> getCustomers() {
        return this.restaurant.getChairs().stream().filter(chair -> chair.getStatus() == ChairStatus.OCCUPIED).map(Chair::getCustomer).collect(Collectors.toList());
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

    public boolean canAddCustomer() {
        // check if restaurant is full
        if (this.getCustomers().size() == restaurant.getChairs().size()) {
            return false;
        }

        // 25% chance to add a customer
        Random rand = new Random();
        return rand.nextInt(4) == 0;
    }

    public void addCustomer() {
        this.getCustomers().add((Customer) customerFactory.create());
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

    public boolean isState(Class<? extends RestaurantState> clazz) {
        return this.restaurant.getState().getClass().equals(clazz);
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

    public int calculateServerPrice() {
        int size = this.restaurant.getServers().size();
        if (size >= 7) {
            return -1;
        }

        return size * 150;
    }

    public void hireServer() {
        // deduct money
        int newServerPrice = this.calculateServerPrice();
        this.removeMoney(newServerPrice);

        this.restaurant.getServers().add((Server) serverFactory.create());
    }

    public int calculateCookPrice() {
        int size = this.restaurant.getCooks().size();
        if (size >= 7) {
            return -1;
        }

        return size * 200;
    }

    public void hireCook() {
        // deduct money
        int newCookPrice = this.calculateCookPrice();
        this.removeMoney(newCookPrice);

        this.restaurant.getCooks().add((Cook) cookFactory.create());
    }

}
