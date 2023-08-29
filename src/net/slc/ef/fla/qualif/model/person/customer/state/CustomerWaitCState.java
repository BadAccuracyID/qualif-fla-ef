package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;

// wait food
public class CustomerWaitCState extends CustomerState {

    int counter = 0;

    public CustomerWaitCState(Customer customer) {
        super(customer);
    }

    @Override
    public String getName() {
        return "wait food";
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        if (counter++ % 4 == 0) {
            customer.getCustomerFacade().decreaseTolerance();
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        RestaurantMediator.PersonRelationStorage relationStorage = customer.getRestaurant().getRestaurantMediator().getRelationStorage();
        return relationStorage.getWaiter(customer);
    }
}
