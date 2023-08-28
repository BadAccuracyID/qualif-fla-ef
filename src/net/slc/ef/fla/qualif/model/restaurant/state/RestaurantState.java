package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public abstract class RestaurantState {

    protected Restaurant restaurant;

    public RestaurantState(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public static boolean isState(Restaurant restaurant, Class<? extends RestaurantState> clazz) {
        return restaurant.getState().getClass().equals(clazz);
    }

    public abstract void onEnter();

    public abstract void processInput(String string);

    public boolean isState(Class<? extends RestaurantState> clazz) {
        return this.restaurant.getState().getClass().equals(clazz);
    }

}
