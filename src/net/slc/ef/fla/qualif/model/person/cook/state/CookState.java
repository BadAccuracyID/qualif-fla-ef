package net.slc.ef.fla.qualif.model.person.cook.state;

import net.slc.ef.fla.qualif.model.person.cook.Cook;
import net.slc.ef.fla.qualif.state.TickableState;

import java.util.concurrent.ExecutorService;

public abstract class CookState extends TickableState {

    protected final Cook cook;

    protected CookState(Cook cook) {
        this.cook = cook;
    }

    @Override
    protected void switchState(TickableState state) {
        this.cook.getCookFacade().switchState(state);
    }

    @Override
    protected void addExecutor(ExecutorService executor) {
        this.cook.getRestaurant().getRestaurantFacade().addExecutor(executor);
    }

    @Override
    protected void removeExecutor(ExecutorService executor) {
        this.cook.getRestaurant().getRestaurantFacade().removeExecutor(executor);
    }

}
