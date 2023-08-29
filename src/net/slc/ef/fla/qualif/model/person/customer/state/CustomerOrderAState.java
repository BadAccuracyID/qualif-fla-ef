package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;

// waiting for available waiter
public class CustomerOrderAState extends CustomerState {

    private int counter = 0;

    public CustomerOrderAState(Customer customer) {
        super(customer);
    }

    @Override
    public String getName() {
        return "order food";
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        customer.getRestaurant().getRestaurantMediator().notify(customer, MediatorAction.REQUEST_WAITER);
        if (counter++ % 2 == 0) {
            customer.getCustomerFacade().decreaseTolerance();
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        return null;
    }
}
