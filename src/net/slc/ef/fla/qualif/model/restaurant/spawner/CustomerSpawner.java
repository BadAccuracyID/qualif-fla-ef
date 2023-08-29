package net.slc.ef.fla.qualif.model.restaurant.spawner;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.model.restaurant.RestaurantFacade;
import net.slc.ef.fla.qualif.observer.IObserver;

import java.util.Random;

public class CustomerSpawner implements IObserver<Restaurant> {

    private final Random random;

    public CustomerSpawner() {
        this.random = new Random();
    }

    @Override
    public void update(Restaurant restaurant) {
        RestaurantFacade restaurantFacade = restaurant.getRestaurantFacade();

        // check for 25% chance of spawning a customer
        if (random.nextInt(4) == 0) {
            restaurantFacade.addCustomer();
        }
    }

}
