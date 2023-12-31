package net.slc.ef.fla.qualif.model.person.chef;

import net.slc.ef.fla.qualif.model.person.state.ServableState;

public class ChefFacade {

    private final Chef chef;

    public ChefFacade(Chef chef) {
        this.chef = chef;
    }

    public void switchState(ServableState state) {
        this.chef.getState().onExit();

        this.chef.getRestaurant().getBookedPersons().add(this.chef);
        this.chef.setState(state);
        this.chef.getState().onEnter();
    }
}
