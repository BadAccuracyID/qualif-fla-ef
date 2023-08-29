package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

public class CustomerArriveState extends CustomerState {


    public CustomerArriveState(Customer customer) {
        super(customer);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        customer.getRestaurant().getRestaurantMediator().notify(customer, MediatorAction.REQUEST_WAITER);
    }

    @Override
    public void onExit() {

    }
}
