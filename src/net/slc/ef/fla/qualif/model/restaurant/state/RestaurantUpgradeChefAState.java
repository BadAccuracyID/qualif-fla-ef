package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.utils.Utils;

public class RestaurantUpgradeChefAState extends RestaurantState {

    private final int price = 150;

    public RestaurantUpgradeChefAState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        Utils.clearScreen();
        System.out.println("----- UPGRADE CHEF <Rp. " + price + "> -----");
        System.out.println();
        System.out.println("        Status      ");
        System.out.printf("     Money: Rp. %d\n", restaurant.getMoney());
        System.out.printf("     Score: %d Points\n", restaurant.getScore());
        System.out.printf("     Size: %d Seats\n", restaurant.getChairs().size());
        System.out.println();

        System.out.println("---------------------------------");
        System.out.println("| No. | Initial | Speed | Skill |");
        System.out.println("---------------------------------");
        int i = 1;
        for (Chef chef : restaurant.getChefs()) {
            System.out.printf("| %2d. | %7s | %5d | %5d |\n", i++, chef.getInitial(), chef.getSpeed(), chef.getSkillLevel());
        }
        System.out.println("---------------------------------");
        System.out.println("Input the number of chef to upgrade [0 to cancel]: ");

    }

    @Override
    public void processInput(String string) {
        int index = Integer.parseInt(string) - 1;
        if (index < 0 || index >= restaurant.getChefs().size()) {
            restaurant.getRestaurantFacade().switchState(RestaurantUpgradingState.class);
            return;
        }

        restaurant.getRestaurantFacade().switchState(RestaurantUpgradeChefBState.class);
    }
}
