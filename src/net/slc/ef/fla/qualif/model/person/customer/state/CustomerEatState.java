package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.restaurant.RestaurantFacade;

public class CustomerEatState extends CustomerState {

    private int delay = 6;

    public CustomerEatState(Customer customer) {
        super(customer);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        delay--;
        if (delay <= 0) {
            this.onExit();
        }
    }

    @Override
    public void onExit() {
        // customer is done eating, remove from chair
        RestaurantFacade restaurantFacade = customer.getRestaurant().getRestaurantFacade();
        restaurantFacade.removeCustomer(customer);
        restaurantFacade.addScore(30 * customer.getCook().getSkillLevel());
    }
}
