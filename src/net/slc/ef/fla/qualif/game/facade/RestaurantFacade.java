package net.slc.ef.fla.qualif.game.facade;

import net.slc.ef.fla.qualif.model.person.Customer;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

import java.util.Random;

public class RestaurantFacade {
    private final Restaurant restaurant;

    public RestaurantFacade(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void generateInitial(Customer customer) {
        // Make sure customer name has at least 2 characters
        if (customer.getName().length() < 2) {
            throw new IllegalArgumentException("Customer name must have at least 2 characters.");
        }

        // Generate two distinct capital letter initials from the name
        Random rand = new Random();
        String name = customer.getName().toUpperCase();
        int index1, index2;
        do {
            do {
                index1 = rand.nextInt(name.length());
                index2 = rand.nextInt(name.length());
            } while (index1 == index2 || !Character.isLetter(name.charAt(index1)) || !Character.isLetter(name.charAt(index2)));

            String initial = name.charAt(index1) + String.valueOf(name.charAt(index2));
            boolean match = restaurant.getCustomers().stream().anyMatch(c -> c.getInitial().equals(initial));
            if (!match) {
                customer.setInitial(initial);
                break;
            }
        } while (true);
    }

}
