package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class RestaurantInitializationState extends RestaurantState {

    public RestaurantInitializationState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        System.out.println("This is some beautiful splash screen!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("No way you messed this up lmao");
            e.printStackTrace();
        }

        restaurant.getRestaurantFacade().switchState(new RestaurantRunningState(restaurant));
    }

    @Override
    public void processInput(String string) {
        // do nothing
    }
}
