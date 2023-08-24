package net.slc.ef.fla.qualif.model.person;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

import java.util.Random;

public class PersonInitialGenerator {

    private final Restaurant restaurant;

    public PersonInitialGenerator(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String generateInitial() {
        String initial;
        Random rand = new Random();
        do {
            char initial1 = (char) (rand.nextInt(26) + 'A');
            char initial2 = (char) (rand.nextInt(26) + 'A');

            String potInitial = String.valueOf(initial1) + initial2;
            boolean match = this.restaurant.getRestaurantFacade().getPersons().stream().anyMatch(c -> c.getInitial().equals(potInitial));
            if (!match) {
                initial = potInitial;
                break;
            }
        } while (true);

        return initial;
    }

}
