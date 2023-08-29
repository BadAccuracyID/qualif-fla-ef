package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.state.ServableState;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.state.MasterState;
import net.slc.ef.fla.qualif.state.TickableState;

public abstract class WaiterState extends ServableState {

    protected final Waiter waiter;

    public WaiterState(Waiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void switchState(ServableState state) {
        this.waiter.getWaiterFacade().switchState(state);
    }

    @Override
    public boolean isState(Class<? extends MasterState> clazz) {
        return waiter.getState().getClass().equals(clazz);
    }
}
