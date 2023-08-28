package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class RestaurantHiringState extends RestaurantState {

    public RestaurantHiringState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        System.out.println("----- HIRING -----");
        System.out.println();
        System.out.println("        Status      ");
        System.out.printf("     Money: Rp. %d\n", restaurant.getMoney());
        System.out.printf("     Score: %d Points\n", restaurant.getScore());
        System.out.printf("     Size: %d Seats\n", restaurant.getChairs().size());
        System.out.println();

        int newWaiterPrice = restaurant.getRestaurantFacade().calculateWaiterPrice();
        System.out.printf("1. Hire Waiter (Rp. %s)\n", newWaiterPrice > 0 ? newWaiterPrice : "Max");

        int newChefPrice = restaurant.getRestaurantFacade().calculateChefPrice();
        System.out.printf("2. Hire Cook (Rp. %s)\n", newChefPrice > 0 ? newChefPrice : "Max");

        System.out.println("3. Back to upgrade menu");
        System.out.print("> ");
    }

    @Override
    public void processInput(String string) {
        switch (string) {
            case "1":
                int newWaiterPrice = restaurant.getRestaurantFacade().calculateWaiterPrice();
                if (newWaiterPrice < 0) {
                    System.out.println("You have reached the maximum number of waiters");
                    break;
                }

                if (!restaurant.getRestaurantFacade().canPurchase(newWaiterPrice)) {
                    System.out.println("You don't have enough money");
                    break;
                }

                System.out.println("Hiring Waiter");
                restaurant.getRestaurantFacade().hireWaiter();
                break;
            case "2":
                int newChefPrice = restaurant.getRestaurantFacade().calculateChefPrice();
                if (newChefPrice < 0) {
                    System.out.println("You have reached the maximum number of cooks");
                    break;
                }

                if (!restaurant.getRestaurantFacade().canPurchase(newChefPrice)) {
                    System.out.println("You don't have enough money");
                    break;
                }

                System.out.println("Hiring Cook");
                restaurant.getRestaurantFacade().hireChef();
                break;
            case "3":
                System.out.println("Back to upgrade menu");
                restaurant.getRestaurantFacade().switchState(RestaurantUpgradingState.class);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }
}
