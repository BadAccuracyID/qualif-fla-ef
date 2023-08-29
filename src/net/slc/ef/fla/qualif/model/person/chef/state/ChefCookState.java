package net.slc.ef.fla.qualif.model.person.chef.state;

import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

public class ChefCookState extends ChefState {

    private int delay;

    public ChefCookState(Chef chef) {
        super(chef);
    }

    @Override
    public String getName() {
        return "cook";
    }

    @Override
    public void onEnter() {
        this.delay = 6 - chef.getSpeed();
    }

    @Override
    public void onTick() {
        this.delay--;
        if (this.delay <= 0) {
            chef.getChefFacade().switchState(new ChefDoneState(chef));
        }
    }

    @Override
    public void onExit() {

    }
}
