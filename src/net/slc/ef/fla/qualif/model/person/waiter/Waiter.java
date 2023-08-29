package net.slc.ef.fla.qualif.model.person.waiter;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.state.ServableState;
import net.slc.ef.fla.qualif.model.person.waiter.state.WaiterIdleState;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Waiter extends AbstractPerson {

    private final WaiterFacade waiterFacade;
    private ServableState state;

    private int speed;

    public Waiter(Restaurant restaurant, String initial) {
        super(restaurant, initial);
        this.waiterFacade = new WaiterFacade(this);

        this.state = new WaiterIdleState(this);
        this.state.onEnter();

        this.speed = 1;
    }

    @Override
    public void tick() {
        this.state.onTick();
    }

    public ServableState getState() {
        return state;
    }

    public void setState(ServableState state) {
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

}
