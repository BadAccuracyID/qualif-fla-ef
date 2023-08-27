package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class RestaurantPausedState extends RestaurantState {

    public RestaurantPausedState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        System.out.println("Restaurant paused");
        System.out.println("Type 'resume' to resume the restaurant");
        System.out.println("Type 'exit' to exit the restaurant");
    }

    @Override
    public void processInput(String string) {
        switch (string) {
            case "resume":
                RestaurantRunningState runningState = new RestaurantRunningState(restaurant);
                restaurant.getRestaurantFacade().switchState(runningState);
                break;
            case "exit":
                System.out.println("Exiting the restaurant");
                restaurant.end();
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }
}
