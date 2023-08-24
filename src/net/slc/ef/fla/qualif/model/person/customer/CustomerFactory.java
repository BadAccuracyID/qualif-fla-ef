package net.slc.ef.fla.qualif.model.person.customer;

import net.slc.ef.fla.qualif.model.person.Person;
import net.slc.ef.fla.qualif.model.person.PersonFactory;
import net.slc.ef.fla.qualif.model.person.PersonInitialGenerator;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.utils.Utils;

import java.util.Random;

public class CustomerFactory implements PersonFactory {

    private final Restaurant restaurant;
    private final PersonInitialGenerator initialsGenerator;

    public CustomerFactory(Restaurant restaurant, PersonInitialGenerator initialsGenerator) {
        this.restaurant = restaurant;
        this.initialsGenerator = initialsGenerator;
    }

    @Override
    public Person create() {
        Random random = new Random();
        String firstName = Utils.customerFirstNames.get(random.nextInt(Utils.customerFirstNames.size()));
        String lastName = Utils.customerLastNames.get(random.nextInt(Utils.customerLastNames.size()));

        Customer customer = new Customer(restaurant, firstName + " " + lastName);
        customer.setTolerance(100);

        String initial = initialsGenerator.generateInitial();
        customer.setInitial(initial);

        return customer;
    }

}
