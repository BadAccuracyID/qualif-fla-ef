package net.slc.ef.fla.qualif.model.restaurant.task;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.model.restaurant.RestaurantFacade;
import net.slc.ef.fla.qualif.utils.Utils;

import java.util.List;
import java.util.Objects;

public class TickerTask implements Runnable {

    private final Restaurant restaurant;
    private final RestaurantFacade restaurantFacade;

    public TickerTask(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.restaurantFacade = restaurant.getRestaurantFacade();
    }

    @Override
    public void run() {
        // print UI
        Utils.clearScreen();
        System.out.println("                    " + restaurant.getName() + " Restaurant is now open!");
        System.out.println();
        System.out.println("                            Status");
        System.out.printf("                         Money  : Rp. %d\n", restaurant.getMoney());
        System.out.printf("                         Score  : %d Points\n", restaurant.getScore());
        System.out.printf("                         Size   : %d Seats\n", restaurant.getChairs().size());
        System.out.println();
        System.out.println("==================================================================================");
        System.out.println("          Customers          |          Waiters          |         Cooks          ");
        System.out.println("==================================================================================");

        for (int i = 0; i < this.getLinesNeeded(); i++) {
            Customer customer;
            if ((customer = this.getCustomer(restaurantFacade.getCustomers(), i)) != null) {
                AbstractPerson server = customer.getState().getServer();
                String serverInitial = server != null ? server.getInitial() : "";

                System.out.printf("%-2s <%-2d>, %15s<%-2s>", customer.getInitial(), customer.getTolerance(), customer.getState().getName(), serverInitial);
            } else {
                System.out.print("                            ");
            }
            System.out.print(" | ");

            Waiter waiter;
            if ((waiter = this.getWaiter(restaurant.getWaiters(), i)) != null) {
                AbstractPerson served = waiter.getState().getServer();
                String servedInitial = served != null ? served.getInitial() : "";

                System.out.printf("<%-2s>, %15s<%-2s>", waiter.getInitial(), waiter.getState().getName(), servedInitial);
            } else {
                System.out.print("                         ");
            }
            System.out.print(" | ");

            Chef chef;
            if ((chef = this.getChef(restaurant.getChefs(), i)) != null) {
                AbstractPerson served = chef.getState().getServer();
                String servedInitial = served != null ? served.getInitial() : "";

                System.out.printf("<%-2s>, %13s<%-2s>", chef.getInitial(), chef.getState().getName(), servedInitial);
            } else {
                System.out.print("                       ");
            }

            System.out.println();
        }

        System.out.println();
        System.out.println("                         Press enter to pause the game ");

        // tick all persons
        restaurantFacade.getPersons().stream()
                .filter(Objects::nonNull)
                .forEach(AbstractPerson::tick);

        // check for spawn
        if (restaurantFacade.getRemainingCapacity() > 0) {
            restaurant.notifyObservers();
        }
    }

    private int getLinesNeeded() {
        // so, each customer is 1 line, and each waiter and cook is 1/2 line
        int personSize = restaurantFacade.getCustomers().size();
        int waiterSize = (int) Math.ceil(restaurant.getWaiters().size() / 2.0);
        int chefSize = (int) Math.ceil(restaurant.getChefs().size() / 2.0);

        return personSize + waiterSize + chefSize;
    }

    private Customer getCustomer(List<Customer> list, int index) {
        if (list.isEmpty()) {
            return null;
        }
        if (index >= list.size()) {
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

    private Chef getChef(List<Chef> list, int index) {
        if (list.isEmpty()) {
            return null;
        }
        if (index >= list.size()) {
            return null;
        }

        return list.get(index);
    }
}
