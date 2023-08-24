package net.slc.ef.fla.qualif.model.person;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public abstract class Person {

    private final Restaurant restaurant;
    private final String name;
    private String initial;

    public Person(Restaurant restaurant, String name) {
        this.restaurant = restaurant;
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getName() {
        return name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

}
