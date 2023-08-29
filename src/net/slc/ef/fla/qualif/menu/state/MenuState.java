package net.slc.ef.fla.qualif.menu.state;

import net.slc.ef.fla.qualif.menu.Menu;
import net.slc.ef.fla.qualif.state.MasterState;

public abstract class MenuState extends MasterState {

    protected final Menu menu;

    protected MenuState(Menu menu) {
        this.menu = menu;
    }

    @Override
    protected void switchState(MasterState state) {
        this.menu.getMenuFacade().switchMenuState(state);
    }

    @Override
    public boolean isState(Class<? extends MasterState> clazz) {
        return this.menu.getCurrentState().getClass().equals(clazz);
    }
}
