package net.slc.ef.fla.qualif.model.restaurant;

import net.slc.ef.fla.qualif.model.person.Person;
import net.slc.ef.fla.qualif.model.person.PersonFactory;
import net.slc.ef.fla.qualif.model.person.PersonInitialGenerator;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.customer.CustomerFactory;
import net.slc.ef.fla.qualif.model.person.server.Server;
import net.slc.ef.fla.qualif.model.person.server.ServerFactory;
import net.slc.ef.fla.qualif.model.restaurant.chair.Chair;
import net.slc.ef.fla.qualif.model.restaurant.chair.ChairStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class RestaurantFacade {

    private final Restaurant restaurant;
    private final PersonFactory customerFactory, serverFactory;

    public RestaurantFacade(Restaurant restaurant) {
        this.restaurant = restaurant;

        PersonInitialGenerator initialsGenerator = new PersonInitialGenerator(this.restaurant);
        this.customerFactory = new CustomerFactory(this.restaurant, initialsGenerator);
        this.serverFactory = new ServerFactory(this.restaurant, initialsGenerator);
    }

    public void addExecutor(ExecutorService executor) {
        restaurant.getExecutorServices().add(executor);
    }

    public void removeExecutor(ExecutorService executor) {
        restaurant.getExecutorServices().remove(executor);
    }


    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();
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

    public void addServer() {
        restaurant.getServers().add((Server) serverFactory.create());
    }

    public void addMoney(int money) {
        this.restaurant.setMoney(this.restaurant.getMoney() + money);
    }

    public void removeMoney(int money) {
        this.restaurant.setMoney(this.restaurant.getMoney() - money);
    }

    public void addScore(int score) {
        this.restaurant.setScore(this.restaurant.getScore() + score);
    }

    public void removeScore(int score) {
        this.restaurant.setScore(this.restaurant.getScore() - score);
    }

}
