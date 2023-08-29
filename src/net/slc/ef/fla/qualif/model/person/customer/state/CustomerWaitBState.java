package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.relation.RelationStorage;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;

// wait food
public class CustomerWaitBState extends CustomerState {

    int counter = 0;

    public CustomerWaitBState(Customer customer) {
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

        if (customer.getTolerance() <= 0) {
            customer.getRestaurant().getRestaurantMediator().notify(customer, MediatorAction.CUSTOMER_LEAVE_NEGATIVE);
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        RelationStorage relationStorage = customer.getRestaurant().getRestaurantMediator().getRelationStorage();
        return relationStorage.getChef(customer);
    }
}
