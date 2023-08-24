package net.slc.ef.fla.qualif.menu;

import net.slc.ef.fla.qualif.Main;
import net.slc.ef.fla.qualif.menu.facade.MenuFacade;
import net.slc.ef.fla.qualif.menu.state.MenuMainState;
import net.slc.ef.fla.qualif.menu.state.MenuState;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class Menu {

    private final Main main;
    private final MenuFacade menuFacade;
    private final Set<ExecutorService> executorServices;
    private MenuState currentState;

    public Menu(Main main) {
        this.main = main;
        this.menuFacade = new MenuFacade(this);
        this.executorServices = new HashSet<>();

        this.setCurrentState(new MenuMainState(this));
        this.getCurrentState().onEnter();
    }

    public MenuState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MenuState currentState) {
        this.currentState = currentState;
    }

    public Set<ExecutorService> getExecutorServices() {
        return executorServices;
    }

    public MenuFacade getMenuFacade() {
        return menuFacade;
    }

}
