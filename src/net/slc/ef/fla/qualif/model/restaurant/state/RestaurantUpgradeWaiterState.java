package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.utils.Utils;

public class RestaurantUpgradeWaiterState extends RestaurantState {

    private final int price = 150;

    public RestaurantUpgradeWaiterState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        Utils.clearScreen();
        System.out.println("----- UPGRADE WAITER <Rp. " + price + "> -----");
        System.out.println();
        System.out.println("        Status      ");
        System.out.printf("     Money: Rp. %d\n", restaurant.getMoney());
        System.out.printf("     Score: %d Points\n", restaurant.getScore());
        System.out.printf("     Size: %d Seats\n", restaurant.getChairs().size());
        System.out.println();

        System.out.println("-------------------------");
        System.out.println("| No. | Initial | Speed |");
        System.out.println("-------------------------");
        int i = 1;
        for (Waiter waiter : restaurant.getWaiters()) {
            System.out.printf("| %2d. | %7s | %5d |\n", i++, waiter.getInitial(), waiter.getSpeed());
        }
        System.out.println("-------------------------");
        System.out.println("Input the number of waiter to upgrade [0 to cancel]: ");
    }

    @Override
    public void processInput(String string) {
        int index = Integer.parseInt(string) - 1;
        if (index < 0 || index >= restaurant.getWaiters().size()) {
            restaurant.setState(new RestaurantUpgradingState(restaurant));
            return;
        }

        if (!restaurant.getRestaurantFacade().canPurchase(price)) {
            System.out.println("You don't have enough money");
            return;
        }

        Waiter waiter = restaurant.getWaiters().get(index);
        if (waiter.getSpeed() >= 5) {
            System.out.println("You have reached the maximum speed");
            return;
        }

        System.out.println("Upgrading waiter " + waiter.getInitial());
        restaurant.getRestaurantFacade().upgradeWaiterSpeed(waiter);
    }
}
