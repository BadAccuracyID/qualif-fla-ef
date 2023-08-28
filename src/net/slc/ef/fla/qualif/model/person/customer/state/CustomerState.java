package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.state.MasterState;
import net.slc.ef.fla.qualif.state.TickableState;

import java.util.concurrent.ExecutorService;

public abstract class CustomerState extends TickableState {

    protected final Customer customer;

    public CustomerState(Customer customer) {
        this.customer = customer;
    }

    @Override
    protected void switchState(TickableState state) {
        this.customer.getCustomerFacade().switchState(state);
    }

    @Override
    protected void addExecutor(ExecutorService executor) {
        this.customer.getRestaurant().getRestaurantFacade().addExecutor(executor);
    }

    @Override
    protected void removeExecutor(ExecutorService executor) {
        this.customer.getRestaurant().getRestaurantFacade().removeExecutor(executor);
    }

    @Override
    public boolean isState(Class<? extends MasterState> clazz) {
        return customer.getState().getClass().equals(clazz);
    }
}
