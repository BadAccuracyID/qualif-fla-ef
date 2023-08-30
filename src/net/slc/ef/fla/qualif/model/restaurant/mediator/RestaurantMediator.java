package net.slc.ef.fla.qualif.model.restaurant.mediator;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefCookState;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefDoneState;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefIdleState;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.customer.state.*;
import net.slc.ef.fla.qualif.model.person.relation.Relation;
import net.slc.ef.fla.qualif.model.person.relation.RelationStorage;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.person.waiter.state.*;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.model.restaurant.RestaurantFacade;

public class RestaurantMediator {

    private final RestaurantFacade restaurantFacade;
    private final RelationStorage relationStorage;

    public RestaurantMediator(Restaurant restaurant) {
        this.restaurantFacade = restaurant.getRestaurantFacade();
        this.relationStorage = new RelationStorage();
    }

    public RelationStorage getRelationStorage() {
        return relationStorage;
    }

    public void notify(AbstractPerson sender, MediatorAction action) {
        switch (action) {
            case REQUEST_WAITER: { // customer wants to order
                assert sender instanceof Customer;
                Customer customer = (Customer) sender;

                Waiter waiter;
                if ((waiter = restaurantFacade.getIdlingWaiter()) != null) {
                    relationStorage.addRelation(new Relation<>(customer, waiter));

                    waiter.getWaiterFacade().switchState(new WaiterTakeOrderState(waiter));
                    customer.getCustomerFacade().switchState(new CustomerOrderBState(customer));
                }
                break;
            }

            case CUSTOMER_ORDER_TAKEN: {
                assert sender instanceof Waiter;
                Waiter waiter = (Waiter) sender;

                Customer customer = relationStorage.getCustomer(waiter);
                relationStorage.addRelation(new Relation<>(customer, waiter));

                waiter.getWaiterFacade().switchState(new WaiterWaitCookState(waiter));
                customer.getCustomerFacade().switchState(new CustomerWaitAState(customer));
                break;
            }

            case REQUEST_COOK: {
                assert sender instanceof Waiter;
                Waiter waiter = (Waiter) sender;

                Chef chef;
                if ((chef = restaurantFacade.getIdlingChef()) != null) { // chef is idling, send order to them
                    Customer customer = relationStorage.getCustomer(waiter);

                    relationStorage.addRelation(new Relation<>(waiter, chef));
                    relationStorage.addRelation(new Relation<>(customer, chef));

                    waiter.getWaiterFacade().switchState(new WaiterIdleState(waiter, "REQUEST_COOK"));
                    chef.getChefFacade().switchState(new ChefCookState(chef));
                    customer.getCustomerFacade().switchState(new CustomerWaitBState(customer));
                } else if ((chef = restaurantFacade.getDoneChef()) != null) { // chef is done cooking, send order to them and take the ready food
                    // store variables
                    Customer chefCustomer = relationStorage.getCustomer(chef);
                    Customer waiterCustomer = relationStorage.getCustomer(waiter);

                    // take the ready food
                    relationStorage.addRelation(new Relation<>(chefCustomer, chef));
                    relationStorage.addRelation(new Relation<>(chefCustomer, waiter));

                    // send order to chef
                    relationStorage.addRelation(new Relation<>(waiterCustomer, chef));
                    relationStorage.addRelation(new Relation<>(waiterCustomer, waiter));

                    // switch states
                    waiter.getWaiterFacade().switchState(new WaiterBringOrderState(waiter));
                    chef.getChefFacade().switchState(new ChefCookState(chef));
                    chefCustomer.getCustomerFacade().switchState(new CustomerWaitCState(chefCustomer));
                    waiterCustomer.getCustomerFacade().switchState(new CustomerWaitBState(waiterCustomer));
                }
                break;
            }

            case CHEF_COOK_DONE: {
                assert sender instanceof Chef;
                Chef chef = (Chef) sender;

                chef.getChefFacade().switchState(new ChefDoneState(chef));
                break;
            }

            case DELIVER_TO_WAITER: { // delivering food from cook to waiter
                assert sender instanceof Chef;
                Chef chef = (Chef) sender;

                Waiter waiter;
                if ((waiter = restaurantFacade.getIdlingWaiter()) != null) {
                    Customer customer = relationStorage.getCustomer(chef);
                    relationStorage.addRelation(new Relation<>(customer, chef));
                    relationStorage.addRelation(new Relation<>(customer, waiter));
                    relationStorage.addRelation(new Relation<>(chef, waiter));

                    chef.getChefFacade().switchState(new ChefIdleState(chef));
                    waiter.getWaiterFacade().switchState(new WaiterBringOrderState(waiter));
                    customer.getCustomerFacade().switchState(new CustomerWaitCState(customer));
                }
                break;
            }

            case WAITER_BRING_ORDER: { // bringing order from cook to waiter
                assert sender instanceof Waiter;
                Waiter waiter = (Waiter) sender;

                waiter.getWaiterFacade().switchState(new WaiterServeState(waiter));
                break;
            }

            case DELIVER_TO_CUSTOMER: { // serving food from waiter to their customer
                assert sender instanceof Waiter;
                Waiter waiter = (Waiter) sender;

                Customer customer = relationStorage.getCustomer(waiter);
                customer.getCustomerFacade().switchState(new CustomerEatState(customer));
                waiter.getWaiterFacade().switchState(new WaiterIdleState(waiter, "DELIVER_TO_CUSTOMER"));
                break;
            }

            case CUSTOMER_LEAVE_POSITIVE: {
                assert sender instanceof Customer;
                Customer customer = (Customer) sender;

                Chef chef = relationStorage.getChef(customer);

                restaurantFacade.removeCustomer(customer);
                restaurantFacade.addScore(30 * chef.getSkillLevel());

                relationStorage.removeRelations(customer);
                break;
            }

            case CUSTOMER_LEAVE_NEGATIVE: {
                assert sender instanceof Customer;
                Customer customer = (Customer) sender;

                restaurantFacade.removeCustomer(customer);
                restaurantFacade.removeScore(300);

                relationStorage.removeRelations(customer);
                break;
            }

            default: {
                throw new IllegalStateException("Unexpected value: " + action);
            }
        }
    }

}
