package net.slc.ef.fla.qualif.model.person.waiter;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Waiter extends AbstractPerson {

    private final WaiterFacade waiterFacade;
    private TickableState state;

    private int speed;
    private Customer servingCustomer;
    private Chef servingChef;

    public Waiter(Restaurant restaurant, String initial) {
        super(restaurant, initial);
        this.waiterFacade = new WaiterFacade(this);
        this.speed = 1;
    }

    @Override
    public void tick() {
        this.state.onTick();
    }

    public TickableState getState() {
        return state;
    }

    public void setState(TickableState state) {
        this.state = state;
    }

    public WaiterFacade getWaiterFacade() {
        return waiterFacade;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Customer getServingCustomer() {
        return servingCustomer;
    }

    public void setServingCustomer(Customer servingCustomer) {
        this.servingCustomer = servingCustomer;
    }

    public Chef getServingChef() {
        return servingChef;
    }

    public void setServingChef(Chef servingChef) {
        this.servingChef = servingChef;
    }
}
