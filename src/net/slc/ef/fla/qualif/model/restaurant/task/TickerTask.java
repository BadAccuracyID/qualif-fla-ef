package net.slc.ef.fla.qualif.model.restaurant.task;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class TickerTask implements Runnable {

    private final Restaurant restaurant;

    public TickerTask(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        // print menu

        // tick all persons
        restaurant.getRestaurantFacade().getPersons().forEach(AbstractPerson::tick);
    }
}
