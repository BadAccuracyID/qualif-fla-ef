package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

public class CustomerOrderState extends CustomerState {

    private int counter = 0;

    protected CustomerOrderState(Customer customer) {
        super(customer);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        if (customer.getWaiter() == null) {
            customer.getRestaurant().getRestaurantMediator().notify(customer, MediatorAction.REQUEST_WAITER);
        }

        // check if customer is still waiting for a waiter
        if (customer.getWaiter() == null) {
            if (counter++ % 2 == 0) {
                customer.getCustomerFacade().decreaseTolerance();
            }
        }
    }

    @Override
    public void onExit() {

    }
}
