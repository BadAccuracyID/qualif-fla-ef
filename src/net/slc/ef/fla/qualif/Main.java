package net.slc.ef.fla.qualif;

import net.slc.ef.fla.qualif.menu.Menu;

public class Main {

    private Menu menu;

    public Main() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (menu != null) {
                menu.getMenuFacade().shutdown();
            }
        }));

        this.start();
    }

    public static void main(String[] args) {
        new Main();
    }

    public Menu getMenu() {
        return menu;
    }

    private void start() {
        this.menu = new Menu(this);
    }

}
