package net.slc.ef.fla.qualif.model.person.chef.state;

import net.slc.ef.fla.qualif.model.person.chef.Chef;

public class ChefIdleState extends ChefState {

    public ChefIdleState(Chef chef) {
        super(chef);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        // do nothing
    }

    @Override
    public void onExit() {

    }
}
