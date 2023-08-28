package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.state.TickableState;

import java.util.concurrent.ExecutorService;

public abstract class WaiterState extends TickableState {

    protected final Waiter waiter;

    public WaiterState(Waiter waiter) {
        this.waiter = waiter;
    }

    @Override
    protected void switchState(TickableState state) {
        this.waiter.getWaiterFacade().switchState(state);
    }

    @Override
    protected void addExecutor(ExecutorService executor) {
        this.waiter.getRestaurant().getRestaurantFacade().addExecutor(executor);
    }

    @Override
    protected void removeExecutor(ExecutorService executor) {
        this.waiter.getRestaurant().getRestaurantFacade().removeExecutor(executor);
    }
}
