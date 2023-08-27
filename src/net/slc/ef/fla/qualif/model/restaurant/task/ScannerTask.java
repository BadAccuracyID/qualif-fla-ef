package net.slc.ef.fla.qualif.model.restaurant.task;

import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

import java.util.Scanner;

public class ScannerTask implements Runnable {

    private final Restaurant restaurant;
    private final Scanner scanner;

    public ScannerTask(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        String input = scanner.nextLine();
        restaurant.getState().processInput(input);
    }
}
