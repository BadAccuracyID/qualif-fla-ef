package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;

public class WaiterWaitCookState extends WaiterState {

    public WaiterWaitCookState(Waiter waiter) {
        super(waiter);
    }

    @Override
    public String getName() {
        return "wait cook";
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

    @Override
    public AbstractPerson getServer() {
        return null;
    }
}
