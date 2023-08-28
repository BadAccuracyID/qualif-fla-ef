package net.slc.ef.fla.qualif.model.person;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public abstract class AbstractPerson {

    private final Restaurant restaurant;
    private final String initial;

    public AbstractPerson(Restaurant restaurant, String initial) {
        this.restaurant = restaurant;
        this.initial = initial;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getInitial() {
        return initial;
    }

    public abstract void tick();

}
