package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.customer.Customer;

public class CustomerOrderState extends CustomerState {

    private int counter = 0;

    public CustomerOrderState(Customer customer) {
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
        if (counter++ % 2 == 0) {
            customer.getCustomerFacade().decreaseTolerance();
        }
    }

    @Override
    public void onExit() {

    }
}
