package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

public class WaiterServeState extends WaiterState {

    private int delay = 1;

    public WaiterServeState(Waiter waiter) {
        super(waiter);
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onTick() {
        delay--;
        if (delay <= 0) {
            waiter.getRestaurant().getRestaurantMediator().notify(waiter, MediatorAction.DELIVER_TO_CUSTOMER);
            waiter.getWaiterFacade().switchState(new WaiterIdleState(waiter));
        }
    }

    @Override
    public void onExit() {

    }

}
