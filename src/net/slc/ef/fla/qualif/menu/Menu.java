package net.slc.ef.fla.qualif.menu;

import net.slc.ef.fla.qualif.Main;
import net.slc.ef.fla.qualif.state.MasterState;
import net.slc.ef.fla.qualif.menu.facade.MenuFacade;
import net.slc.ef.fla.qualif.menu.state.MenuMainState;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class Menu {

    private final Main main;
    private final MenuFacade menuFacade;
    private final Set<ExecutorService> executorServices;
    private MasterState currentState;

    public Menu(Main main) {
        this.main = main;
        this.menuFacade = new MenuFacade(this);
        this.executorServices = new HashSet<>();

        this.setCurrentState(new MenuMainState(this));
        this.getCurrentState().onEnter();
    }

    public MasterState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MasterState currentState) {
        this.currentState = currentState;
    }

    public Set<ExecutorService> getExecutorServices() {
        return executorServices;
    }

    public MenuFacade getMenuFacade() {
        return menuFacade;
    }

}
