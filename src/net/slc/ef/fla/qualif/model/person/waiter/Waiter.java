package net.slc.ef.fla.qualif.model.person.waiter;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.state.ServableState;
import net.slc.ef.fla.qualif.model.person.waiter.state.WaiterIdleState;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Waiter) {
            Waiter other = (Waiter) obj;
            return this.getInitial().equals(other.getInitial());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getInitial());
    }
}
