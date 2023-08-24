package net.slc.ef.fla.qualif.menu.facade;

import net.slc.ef.fla.qualif.menu.Menu;
import net.slc.ef.fla.qualif.menu.state.MenuState;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;

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

    public void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        scanner.nextLine();
    }

    public void shutdown() {
        this.scanner.close();
        this.menu.getExecutorServices().forEach(ExecutorService::shutdownNow);
    }
}
