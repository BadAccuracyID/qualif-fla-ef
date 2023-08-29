package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.relation.RelationStorage;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;

public class WaiterServeState extends WaiterState {

    private int delay = 1;

    public WaiterServeState(Waiter waiter) {
        super(waiter);
    }

    @Override
    public String getName() {
        return "serving food";
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onTick() {
        delay--;
        if (delay <= 0) {
            waiter.getRestaurant().getRestaurantMediator().notify(waiter, MediatorAction.DELIVER_TO_CUSTOMER);
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
