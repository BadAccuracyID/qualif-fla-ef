package net.slc.ef.fla.qualif.menu.state;

import net.slc.ef.fla.qualif.menu.Menu;

public class MenuExitState extends MenuState {
    protected MenuExitState(Menu menu) {
        super(menu);
    }

    @Override
    public void onEnter() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onExit() {

    }
}
