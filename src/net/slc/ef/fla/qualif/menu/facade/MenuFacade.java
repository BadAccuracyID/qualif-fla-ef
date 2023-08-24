package net.slc.ef.fla.qualif.menu.facade;

import net.slc.ef.fla.qualif.state.MasterState;
import net.slc.ef.fla.qualif.menu.Menu;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class MenuFacade {

    private final Menu menu;
    private final Scanner scanner;

    public MenuFacade(Menu menu) {
        this.menu = menu;
        this.scanner = new Scanner(System.in);
    }

    public void switchMenuState(MasterState state) {
        this.menu.getCurrentState().onExit();

        this.menu.setCurrentState(state);
        this.menu.getCurrentState().onEnter();
    }

    public String readString() {
        return scanner.nextLine();
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
