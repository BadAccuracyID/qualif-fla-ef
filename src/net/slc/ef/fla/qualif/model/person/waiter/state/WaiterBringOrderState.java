package net.slc.ef.fla.qualif.model.person.waiter.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.relation.RelationStorage;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;

public class WaiterBringOrderState extends WaiterState {

    private int delay = 1;

    public WaiterBringOrderState(Waiter waiter) {
        super(waiter);
    }

    @Override
    public String getName() {
        return "bring order";
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        delay--;
        if (delay <= 0) {
            waiter.getRestaurant().getRestaurantMediator().notify(waiter, MediatorAction.WAITER_BRING_ORDER);
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        RelationStorage relationStorage = waiter.getRestaurant().getRestaurantMediator().getRelationStorage();
        return relationStorage.getChef(waiter);
    }
}
