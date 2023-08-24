package net.slc.ef.fla.qualif;

import net.slc.ef.fla.qualif.menu.Menu;

public class Main {

    public Main() {
        this.start();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void start() {
        new Menu(this);
    }

}
