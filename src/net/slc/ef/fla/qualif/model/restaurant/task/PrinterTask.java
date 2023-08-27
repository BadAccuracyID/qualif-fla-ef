package net.slc.ef.fla.qualif.model.restaurant.task;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class PrinterTask implements Runnable {

    private final Restaurant restaurant;

    public PrinterTask(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}
