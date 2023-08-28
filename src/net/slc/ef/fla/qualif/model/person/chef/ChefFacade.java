package net.slc.ef.fla.qualif.model.person.chef;

import net.slc.ef.fla.qualif.state.TickableState;

public class ChefFacade {

    private final Chef chef;

    public ChefFacade(Chef chef) {
        this.chef = chef;
    }

    public void switchState(TickableState state) {
        this.chef.getState().onExit();

        this.chef.setState(state);
        this.chef.getState().onEnter();
    }
}
