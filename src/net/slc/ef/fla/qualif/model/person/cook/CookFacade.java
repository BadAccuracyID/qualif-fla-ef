package net.slc.ef.fla.qualif.model.person.cook;

import net.slc.ef.fla.qualif.state.TickableState;

public class CookFacade {

    private final Cook cook;

    public CookFacade(Cook cook) {
        this.cook = cook;
    }

    public void switchState(TickableState state) {
        this.cook.getState().onExit();

        this.cook.setState(state);
        this.cook.getState().onEnter();
    }
}
