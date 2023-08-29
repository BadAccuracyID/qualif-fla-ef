package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;

public class CustomerEatState extends CustomerState {

    private int delay = 6;

    public CustomerEatState(Customer customer) {
        super(customer);
    }

    @Override
    public String getName() {
        return "eat";
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        delay--;
        if (delay <= 0) {
            customer.getRestaurant().getRestaurantMediator().notify(customer, MediatorAction.CUSTOMER_LEAVE_POSITIVE);
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        return null;
    }
}
