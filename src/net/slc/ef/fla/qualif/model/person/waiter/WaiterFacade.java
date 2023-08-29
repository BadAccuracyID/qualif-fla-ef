package net.slc.ef.fla.qualif.model.person.waiter;

import net.slc.ef.fla.qualif.model.person.state.ServableState;

public class WaiterFacade {

    private final Waiter waiter;

    public WaiterFacade(Waiter waiter) {
        this.waiter = waiter;
    }

    public void switchState(ServableState state) {
        this.waiter.getState().onExit();

        this.waiter.setState(state);
        this.waiter.getState().onEnter();
    }
}
