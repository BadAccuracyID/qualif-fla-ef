package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

public class WaiterOrderState extends WaiterState {

    public WaiterOrderState(Waiter waiter) {
        super(waiter);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        waiter.getRestaurant().getRestaurantMediator().notify(waiter, MediatorAction.REQUEST_COOK);
    }

    @Override
    public void onExit() {

    }
}