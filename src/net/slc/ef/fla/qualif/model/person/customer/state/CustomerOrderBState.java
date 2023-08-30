package net.slc.ef.fla.qualif.model.person.customer.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.relation.RelationStorage;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;

// order and waiter is serving
public class CustomerOrderBState extends CustomerState {

    public CustomerOrderBState(Customer customer) {
        super(customer);
    }

    @Override
    public String getName() {
        return "order food";
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {
        RelationStorage relationStorage = customer.getRestaurant().getRestaurantMediator().getRelationStorage();
        if (relationStorage.getCustomer((Waiter) this.getServer()) != this.customer) {
            System.exit(1);
        }

    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        RelationStorage relationStorage = customer.getRestaurant().getRestaurantMediator().getRelationStorage();
        return relationStorage.getWaiter(customer);
    }
}
