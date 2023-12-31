package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.relation.RelationStorage;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

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
            waiter.getRestaurant().getRestaurantMediator().notify(waiter, MediatorAction.CUSTOMER_ORDER_TAKEN);
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        RelationStorage relationStorage = waiter.getRestaurant().getRestaurantMediator().getRelationStorage();
        return relationStorage.getCustomer(waiter);
    }

}
