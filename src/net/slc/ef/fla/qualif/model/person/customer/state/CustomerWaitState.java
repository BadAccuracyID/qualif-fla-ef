package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.customer.Customer;

public class CustomerWaitState extends CustomerState {

    int counter = 0;

    public CustomerWaitState(Customer customer) {
        super(customer);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        if (customer.getWaiter() == null && customer.getChef() == null) {
            if (counter++ % 4 == 0) {
                customer.getCustomerFacade().decreaseTolerance();
            }
        }
    }

    @Override
    public void onExit() {

    }
}
