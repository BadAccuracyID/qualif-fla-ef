package net.slc.ef.fla.qualif.menu.state;

import net.slc.ef.fla.qualif.menu.Menu;
import net.slc.ef.fla.qualif.utils.Utils;

public class MenuMainState extends MenuState {

    public MenuMainState(Menu menu) {
        super(menu);
    }

    @Override
    public void onEnter() {
        Utils.clearScreen();
        System.out.println("Welcome to Restaurant Tycoon!");

        menu.getMenuFacade().promptEnterKey();
        this.sendMenu();
    }

    private void sendMenu() {
        Utils.clearScreen();
        System.out.println("1. Play New Restaurant");
        System.out.println("2. High Score");
        System.out.println("3. Exit");
        System.out.print(">> ");

        String input = menu.getMenuFacade().readString();
        switch (input) {
            case "1":
                this.switchState(new MenuPlayState(menu));
                break;
            case "2":
                this.switchState(new MenuHighScoreState(menu));
                break;
            case "3":
                this.switchState(new MenuExitState(menu));
                break;
            default:
                this.sendMenu();
                break;
        }
    }

    @Override
    public void onExit() {
    }
}
