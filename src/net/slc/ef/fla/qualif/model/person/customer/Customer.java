package net.slc.ef.fla.qualif.model.person.customer;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.customer.state.CustomerArriveState;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Customer extends AbstractPerson {

    private final CustomerFacade customerFacade;
    private TickableState state;

    private int tolerance;

    public Customer(Restaurant restaurant, String name) {
        super(restaurant, name);
        this.customerFacade = new CustomerFacade(this);

        this.state = new CustomerArriveState(this);
        this.state.onEnter();
    }

    @Override
    public void tick() {
        this.state.onTick();
    }

    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    public int getTolerance() {
        return tolerance;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public TickableState getState() {
        return state;
    }

    public void setState(TickableState state) {
        this.state = state;
    }

}
