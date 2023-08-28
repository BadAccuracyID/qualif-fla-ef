package net.slc.ef.fla.qualif.model.restaurant.task;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.cook.Cook;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.utils.Utils;

import java.util.List;

public class TickerTask implements Runnable {

    private final Restaurant restaurant;

    public TickerTask(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        // print UI
        Utils.clearScreen();
        System.out.println("----- " + restaurant.getName() + " -----");
        System.out.println();
        System.out.println("        Status      ");
        System.out.printf("     Money: Rp. %d\n", restaurant.getMoney());
        System.out.printf("     Score: %d Points\n", restaurant.getScore());
        System.out.printf("     Size: %d Seats\n", restaurant.getChairs().size());
        System.out.println();
        System.out.println("==================================================================================");
        System.out.println("          Customers          |          Waiters          |         Cooks          ");
        System.out.println("==================================================================================");

        for (int i = 0; i < restaurant.getRestaurantFacade().getPersons().size(); i++) {
            // <initial> <Patience> <Status><Server> | <Initial>,<Status><Cook> | <Initial>,<Status><Customer>
            Customer customer;
            if ((customer = this.getCustomer(restaurant.getRestaurantFacade().getCustomers(), i)) != null) {
                System.out.printf("%-2s <%-2d>,%-5s<%-2s>", customer.getInitial(), customer.getTolerance(), "wait food", customer.getInitial());
            } else {
                System.out.print("                          ");
            }
            System.out.print(" | ");

            Waiter waiter;
            if ((waiter = this.getWaiter(restaurant.getWaiters(), i)) != null) {
                System.out.printf("<%-2s>, %17s<%-2s>", waiter.getInitial(), "take order", waiter.getInitial());
            } else {
                System.out.print("                           ");
            }
            System.out.print(" | ");

            Cook cook;
            if ((cook = this.getCook(restaurant.getCooks(), i)) != null) {
                System.out.printf("<%-2s>, %13s<%-2s>", cook.getInitial(), "cook", cook.getInitial());
            } else {
                System.out.print("                       ");
            }

            System.out.println();
        }

        // tick all persons
        if (restaurant.getRestaurantFacade().getPersons().isEmpty()) {
            return;
        }

        restaurant.getRestaurantFacade().getPersons().forEach(AbstractPerson::tick);
    }

    private Customer getCustomer(List<Customer> list, int index) {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(index);
    }

    private Waiter getWaiter(List<Waiter> list, int index) {
        if (list.isEmpty()) {
            return null;
        }
        if (index >= list.size()) {
            return null;
        }

        return list.get(index);
    }

    private Cook getCook(List<Cook> list, int index) {
        if (list.isEmpty()) {
            return null;
        }
        if (index >= list.size()) {
            return null;
        }

        return list.get(index);
    }
}
