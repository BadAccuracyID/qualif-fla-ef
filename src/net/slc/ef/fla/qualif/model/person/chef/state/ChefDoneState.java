package net.slc.ef.fla.qualif.model.person.chef.state;

import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

public class ChefDoneState extends ChefState {
    public ChefDoneState(Chef chef) {
        super(chef);
    }

    @Override
    public String getName() {
        return "done";
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        chef.getRestaurant().getRestaurantMediator().notify(chef, MediatorAction.DELIVER_TO_WAITER);
    }

    @Override
    public void onExit() {

    }
}
