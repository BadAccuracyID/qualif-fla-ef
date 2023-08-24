package net.slc.ef.fla.qualif.model.person.server;

import net.slc.ef.fla.qualif.model.person.Person;
import net.slc.ef.fla.qualif.model.person.PersonFactory;
import net.slc.ef.fla.qualif.model.person.PersonInitialGenerator;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.utils.Utils;

import java.util.Random;

public class ServerFactory implements PersonFactory {

    private final Restaurant restaurant;
    private final PersonInitialGenerator initialsGenerator;

    public ServerFactory(Restaurant restaurant, PersonInitialGenerator initialsGenerator) {
        this.restaurant = restaurant;
        this.initialsGenerator = initialsGenerator;
    }

    @Override
    public Person create() {
        Random random = new Random();
        String firstName = Utils.customerFirstNames.get(random.nextInt(Utils.customerFirstNames.size()));
        String lastName = Utils.customerLastNames.get(random.nextInt(Utils.customerLastNames.size()));

        Server server = new Server(restaurant, firstName + " " + lastName);

        String initial = initialsGenerator.generateInitial();
        server.setInitial(initial);

        return server;
    }

}
