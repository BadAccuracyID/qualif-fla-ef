package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.waiter.Waiter;

public class WaiterTakeOrderState extends WaiterState {

    private int delay;

    public WaiterTakeOrderState(Waiter waiter) {
        super(waiter);
    }

    @Override
    public String getName() {
        return "take order";
    }

    @Override
    public void onEnter() {
        delay = 6 - waiter.getSpeed();
    }

    @Override
    public void onTick() {
        delay--;
        if (delay <= 0) {
            waiter.getWaiterFacade().switchState(new WaiterWaitCookState(waiter));
        }
    }

    @Override
    public void onExit() {
    }

}
