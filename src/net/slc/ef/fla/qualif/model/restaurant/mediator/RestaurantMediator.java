package net.slc.ef.fla.qualif.model.restaurant.mediator;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.cook.Cook;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.customer.state.CustomerEatState;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.person.waiter.state.WaiterServeState;
import net.slc.ef.fla.qualif.model.person.waiter.state.WaiterTakeOrderState;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.model.restaurant.RestaurantFacade;

public class RestaurantMediator {

    private final Restaurant restaurant;
    private final RestaurantFacade restaurantFacade;

    public RestaurantMediator(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.restaurantFacade = restaurant.getRestaurantFacade();
    }

    public void notify(AbstractPerson sender, MediatorAction action) {
        switch (action) {
            case REQUEST_WAITER: { // customer wants to order
                Waiter availableWaiter = restaurantFacade.getIdlingWaiter();
                if (availableWaiter == null) {
                    return;
                }

                assert sender instanceof Customer;
                availableWaiter.setServingCustomer((Customer) sender);
                availableWaiter.setState(new WaiterTakeOrderState(availableWaiter));
                break;
            }

            case DELIVER_TO_WAITER: { // delivering food from cook to waiter
                Waiter availableWaiter = restaurantFacade.getIdlingWaiter();
                if (availableWaiter == null) {
                    return;
                }

                assert sender instanceof Cook;
                Cook cook = (Cook) sender;
                availableWaiter.setServingCook(cook);
                availableWaiter.setServingCustomer(cook.getServingCustomer());
                availableWaiter.setState(new WaiterServeState(availableWaiter));
                break;
            }

            case DELIVER_TO_CUSTOMER: { // delivering food from waiter to their customer
                assert sender instanceof Waiter;

                Waiter waiter = (Waiter) sender;
                Customer customer = waiter.getServingCustomer();
                customer.setCook(waiter.getServingCook());
                customer.getCustomerFacade().switchState(new CustomerEatState(customer));
                break;
            }

            default: {
                throw new IllegalStateException("Unexpected value: " + action);
            }
        }
    }


}
