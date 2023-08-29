package net.slc.ef.fla.qualif.model.restaurant.state;

import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class RestaurantUpgradeChefBState extends RestaurantState {

    private final int price = 150;

    public RestaurantUpgradeChefBState(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public void onEnter() {
        System.out.println("Select attribute to upgrade [skill | speed | 0 to cancel]: ");

    }

    @Override
    public void processInput(String string) {
        if (string.equals("0")) {
            restaurant.getRestaurantFacade().switchState(RestaurantUpgradeChefAState.class);
            return;
        }

        Chef chef = restaurant.getRestaurantFacade().getUpgradingChef();
        if (chef.getSkillLevel() >= 5) {
            System.out.println("Chef skill level is already maxed");
            return;
        }

        if (!restaurant.getRestaurantFacade().canPurchase(price)) {
            System.out.println("You don't have enough money");
            return;
        }

        switch (string) {
            case "skill":
                restaurant.getRestaurantFacade().upgradeChefSkill(chef);
                break;
            case "speed":
                restaurant.getRestaurantFacade().upgradeChefSpeed(chef);
                break;
            default:
                System.out.println("Invalid input");
                return;
        }

        restaurant.getRestaurantFacade().switchState(RestaurantUpgradingState.class);
    }
}
