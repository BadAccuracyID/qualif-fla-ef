package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;

public class WaiterIdleState extends WaiterState {

    private final String s;

    public WaiterIdleState(Waiter waiter) {
        super(waiter);
        this.s = "none";
    }

    public WaiterIdleState(Waiter waiter, String s) {
        super(waiter);
        this.s = s;
    }

    @Override
    public String getName() {
        return "idle_" + s.charAt(0);
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
