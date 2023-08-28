package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class RestaurantRunningState extends RestaurantState {

    public RestaurantRunningState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        // do nothing
    }

    @Override
    public void processInput(String string) {
        // pause switch state to paused
        System.out.println("Restaurant paused");
        restaurant.getRestaurantFacade().switchState(RestaurantPausedState.class);
    }

}
