package net.slc.ef.fla.qualif.model.person.customer;

import net.slc.ef.fla.qualif.model.person.Person;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Customer extends Person {

    private final CustomerFacade customerFacade;
    private int tolerance;
    private TickableState state;
    public Customer(Restaurant restaurant, String name) {
        super(restaurant, name);
        this.customerFacade = new CustomerFacade(this);
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
