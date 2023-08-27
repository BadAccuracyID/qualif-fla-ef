package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public abstract class RestaurantState {

    protected Restaurant restaurant;

    public RestaurantState(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public abstract void onEnter();

    public abstract void processInput(String string);

}
