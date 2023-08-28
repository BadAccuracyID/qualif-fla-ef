package net.slc.ef.fla.qualif.model.person.chef.state;

import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

public class ChefCookState extends ChefState {

    private int delay;

    public ChefCookState(Chef chef) {
        super(chef);
    }

    @Override
    public void onEnter() {
        this.delay = 6 - chef.getSpeed();
    }

    @Override
    public void onTick() {
        this.delay--;
        if (this.delay <= 0) {
            chef.getRestaurant().getRestaurantMediator().notify(chef, MediatorAction.DELIVER_TO_WAITER);
        }
    }

    @Override
    public void onExit() {

    }
}
