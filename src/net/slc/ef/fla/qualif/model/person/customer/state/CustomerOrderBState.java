package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.customer.Customer;

// order and waiter is serving
public class CustomerOrderBState extends CustomerState {

    public CustomerOrderBState(Customer customer) {
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

    }

    @Override
    public void onExit() {

    }
}
