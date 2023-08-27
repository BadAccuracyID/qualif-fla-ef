package net.slc.ef.fla.qualif;

import net.slc.ef.fla.qualif.menu.Menu;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

import java.util.ArrayList;

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
//        this.menu = new Menu(this);
        Restaurant ef = Restaurant.getInstance("EF", new ArrayList<>());
        ef.start();
    }

}
