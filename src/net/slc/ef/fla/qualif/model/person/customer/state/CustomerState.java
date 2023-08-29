package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.state.ServableState;
import net.slc.ef.fla.qualif.state.MasterState;

public abstract class CustomerState extends ServableState {

    protected final Customer customer;

    public CustomerState(Customer customer) {
        this.customer = customer;
    }

    @Override
    protected void switchState(ServableState state) {
        this.customer.getCustomerFacade().switchState(state);
    }

    @Override
    public boolean isState(Class<? extends MasterState> clazz) {
        return customer.getState().getClass().equals(clazz);
    }

}
