package net.slc.ef.fla.qualif.model.person.chef.state;

import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.state.TickableState;

import java.util.concurrent.ExecutorService;

public abstract class ChefState extends TickableState {

    protected final Chef chef;

    protected ChefState(Chef chef) {
        this.chef = chef;
    }

    @Override
    protected void switchState(TickableState state) {
        this.chef.getChefFacade().switchState(state);
    }

    @Override
    protected void addExecutor(ExecutorService executor) {
        this.chef.getRestaurant().getRestaurantFacade().addExecutor(executor);
    }

    @Override
    protected void removeExecutor(ExecutorService executor) {
        this.chef.getRestaurant().getRestaurantFacade().removeExecutor(executor);
    }

}
