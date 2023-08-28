package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class RestaurantUpgradingState extends RestaurantState {

    public RestaurantUpgradingState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        System.out.println("----- UPGRADING -----");
        System.out.println();
        System.out.println("        Status      ");
        System.out.printf("     Money: Rp. %d\n", restaurant.getMoney());
        System.out.printf("     Score: %d Points\n", restaurant.getScore());
        System.out.printf("     Size: %d Seats\n", restaurant.getChairs().size());
        System.out.println();

        int newSeatPrice = restaurant.getRestaurantFacade().calculateSeatPrice();
        System.out.printf("1. Add more seats (Rp. %s)\n", newSeatPrice > 0 ? newSeatPrice : "Max");

        System.out.println("2. Hire more employees");

        System.out.printf("3. Upgrade Waiter (Rp. %d)\n", 0);

        System.out.printf("4. Upgrade Cook (Rp. %d)\n", 0);

        System.out.println("5. Back to pause menu");
        System.out.print("> ");
    }

    @Override
    public void processInput(String string) {
        switch (string) {
            case "1":
                int newSeatPrice = restaurant.getRestaurantFacade().calculateSeatPrice();
                if (newSeatPrice < 0) {
                    System.out.println("You have reached the maximum number of seats");
                    break;
                }

                if (!restaurant.getRestaurantFacade().canPurchase(newSeatPrice)) {
                    System.out.println("You don't have enough money");
                    break;
                }

                System.out.println("Adding more seats");
                restaurant.getRestaurantFacade().addChair();
                break;
            case "2":
                System.out.println("Hiring more employees");
                restaurant.getRestaurantFacade().switchState(RestaurantHiringState.class);
                break;
            case "3":
                System.out.println("Upgrading Waiter");
                restaurant.getRestaurantFacade().switchState(RestaurantUpgradeWaiterState.class);
                break;
            case "4":
                System.out.println("Upgrading Cook");
                restaurant.getRestaurantFacade().switchState(RestaurantUpgradeChefState.class);
                break;
            case "5":
                System.out.println("Back to pause menu");
                restaurant.getRestaurantFacade().switchState(RestaurantPausedState.class);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }
}
