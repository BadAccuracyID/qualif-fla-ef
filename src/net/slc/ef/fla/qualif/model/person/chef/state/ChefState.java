package net.slc.ef.fla.qualif.model.person.chef.state;

import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.state.ServableState;
import net.slc.ef.fla.qualif.state.MasterState;
import net.slc.ef.fla.qualif.state.TickableState;

public abstract class ChefState extends ServableState {

    protected final Chef chef;

    public ChefState(Chef chef) {
        this.chef = chef;
    }

    @Override
    protected void switchState(ServableState state) {
        this.chef.getChefFacade().switchState(state);
    }

    @Override
    public boolean isState(Class<? extends MasterState> clazz) {
        return this.chef.getState().getClass().equals(clazz);
    }
}
