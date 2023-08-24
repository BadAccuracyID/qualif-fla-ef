package net.slc.ef.fla.qualif.menu.facade;

import net.slc.ef.fla.qualif.menu.Menu;
import net.slc.ef.fla.qualif.menu.state.MenuState;

import java.util.Scanner;

public class MenuFacade {

    private final Menu menu;
    private final Scanner scanner;

    public MenuFacade(Menu menu) {
        this.menu = menu;
        this.scanner = new Scanner(System.in);
    }

    public void switchMenuState(MenuState state) {
        this.menu.getCurrentState().onExit();

        this.menu.setCurrentState(state);
        this.menu.getCurrentState().onEnter();
    }

    public String readString() {
        String read = scanner.nextLine();
        return read;
    }

}
