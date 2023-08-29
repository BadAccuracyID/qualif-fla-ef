package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.waiter.Waiter;

public class WaiterTakeOrderState extends WaiterState {

    private int delay;

    public WaiterTakeOrderState(Waiter waiter) {
        super(waiter);
    }

    @Override
    public void onEnter() {
        delay = 6 - waiter.getSpeed();
    }

    @Override
    public void onTick() {
        delay--;
        if (delay <= 0) {
            waiter.getWaiterFacade().switchState(new WaiterOrderState(waiter));
        }
    }

    @Override
    public void onExit() {
        waiter.setServingCustomer(null);
    }

}
