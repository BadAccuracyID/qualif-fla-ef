package net.slc.ef.fla.qualif.model.person.waiter;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Waiter extends AbstractPerson {

    private final WaiterFacade waiterFacade;
    private TickableState state;

    public Waiter(Restaurant restaurant, String initial) {
        super(restaurant, initial);
        this.waiterFacade = new WaiterFacade(this);
    }

    @Override
    public void tick() {

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

}
