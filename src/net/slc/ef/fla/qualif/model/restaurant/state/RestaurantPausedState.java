package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class RestaurantPausedState extends RestaurantState {

    public RestaurantPausedState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        System.out.println("----- PAUSED -----");
        System.out.println();
        System.out.println("        Status      ");
        System.out.printf("     Money: Rp. %d\n", restaurant.getMoney());
        System.out.printf("     Score: %d Points\n", restaurant.getScore());
        System.out.printf("     Size: %d Seats\n", restaurant.getChairs().size());
        System.out.println();
        System.out.println("1. Continue business");
        System.out.println("2. Upgrade Restaurant");
        System.out.println("3. Exit");
        System.out.print("> ");
    }

    @Override
    public void processInput(String string) {
        switch (string) {
            case "1":
                restaurant.getRestaurantFacade().switchState(RestaurantRunningState.class);
                break;
            case "2":
                System.out.println("Upgrading the restaurant");
                restaurant.getRestaurantFacade().switchState(RestaurantUpgradingState.class);
                break;
            case "3":
                System.out.println("Exiting the restaurant");
                restaurant.getRestaurantFacade().end();
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }
}
