package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;

public class WaiterIdleState extends WaiterState {

    public WaiterIdleState(Waiter waiter) {
        super(waiter);
    }

    @Override
    public String getName() {
        return "idle";
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void onTick() {

    }

    @Override
    public AbstractPerson getServer() {
        return null;
    }
}
