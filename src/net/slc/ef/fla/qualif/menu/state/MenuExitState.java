package net.slc.ef.fla.qualif.menu.state;

import net.slc.ef.fla.qualif.menu.Menu;

public class MenuExitState extends MenuState {

    protected MenuExitState(Menu menu) {
        super(menu);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onEnter() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

    @Override
    public void onExit() {

    }
}
