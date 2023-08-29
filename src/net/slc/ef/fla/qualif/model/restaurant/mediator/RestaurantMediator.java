package net.slc.ef.fla.qualif.model.restaurant.mediator;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefCookState;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefDoneState;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefIdleState;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.customer.state.*;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.person.waiter.state.*;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.model.restaurant.RestaurantFacade;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMediator {

    private final RestaurantFacade restaurantFacade;
    private final PersonRelationStorage personRelationStorage;

    public RestaurantMediator(Restaurant restaurant) {
        this.restaurantFacade = restaurant.getRestaurantFacade();
        this.personRelationStorage = new PersonRelationStorage(new ArrayList<>());
    }

    public PersonRelationStorage getRelationStorage() {
        return personRelationStorage;
    }

    public void notify(AbstractPerson sender, MediatorAction action) {
        switch (action) {
            case REQUEST_WAITER: { // customer wants to order
                assert sender instanceof Customer;
                Customer customer = (Customer) sender;

                Waiter availableWaiter;
                if ((availableWaiter = restaurantFacade.getIdlingWaiter()) != null) {
                    personRelationStorage.assignWaiter(customer, availableWaiter);

                    customer.getCustomerFacade().switchState(new CustomerOrderBState(customer));
                    availableWaiter.getWaiterFacade().switchState(new WaiterTakeOrderState(availableWaiter));
                }
                break;
            }

            case CUSTOMER_ORDER_TAKEN: {
                assert sender instanceof Waiter;
                Waiter waiter = (Waiter) sender;

                Customer customer = personRelationStorage.getCustomer(waiter);

                waiter.getWaiterFacade().switchState(new WaiterWaitCookState(waiter));
                customer.getCustomerFacade().switchState(new CustomerWaitAState(customer));
                break;
            }

            case REQUEST_COOK: {
                assert sender instanceof Waiter;
                Waiter waiter = (Waiter) sender;

                Chef chef;
                if ((chef = restaurantFacade.getIdlingChef()) != null) { // chef is idling, send order to them
                    Customer customer = personRelationStorage.getCustomer(waiter);

                    personRelationStorage.assignChef(waiter, chef);
                    personRelationStorage.assignChef(customer, chef);

                    waiter.getWaiterFacade().switchState(new WaiterIdleState(waiter));
                    chef.getChefFacade().switchState(new ChefCookState(chef));
                    customer.getCustomerFacade().switchState(new CustomerWaitBState(customer));
                } else if ((chef = restaurantFacade.getDoneChef()) != null) { // chef is done cooking, send order to them and take the ready food
                    // store variables
                    Customer chefCustomer = personRelationStorage.getCustomer(chef);
                    Customer waiterCustomer = personRelationStorage.getCustomer(waiter);

                    // take the ready food
                    personRelationStorage.assignChef(chefCustomer, chef);
                    personRelationStorage.assignWaiter(chefCustomer, waiter);

                    // send order to chef
                    personRelationStorage.assignChef(waiterCustomer, chef);
                    personRelationStorage.assignWaiter(waiterCustomer, waiter);

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
                    Customer customer = personRelationStorage.getCustomer(chef);
                    personRelationStorage.assignChef(customer, chef);
                    personRelationStorage.assignWaiter(customer, waiter);

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

                Customer customer = personRelationStorage.getCustomer(waiter);
                customer.getCustomerFacade().switchState(new CustomerEatState(customer));
                waiter.getWaiterFacade().switchState(new WaiterIdleState(waiter));
                break;
            }

            case CUSTOMER_LEAVE_POSITIVE: {
                assert sender instanceof Customer;
                Customer customer = (Customer) sender;

                Chef chef = personRelationStorage.getChef(customer);

                restaurantFacade.removeCustomer(customer);
                restaurantFacade.addScore(30 * chef.getSkillLevel());

                personRelationStorage.remove(customer);
                break;
            }

            case CUSTOMER_LEAVE_NEGATIVE: {
                assert sender instanceof Customer;
                Customer customer = (Customer) sender;

                restaurantFacade.removeCustomer(customer);
                restaurantFacade.removeScore(300);

                personRelationStorage.remove(customer);
                break;
            }

            default: {
                throw new IllegalStateException("Unexpected value: " + action);
            }
        }
    }

    public enum PersonRelationType {
        CUSTOMER_WAITER,
        CUSTOMER_CHEF,
        WAITER_CUSTOMER,
        WAITER_CHEF,
        CHEF_CUSTOMER,
        CHEF_WAITER
    }

    public static class PersonRelationKey {
        private final AbstractPerson person;
        private final PersonRelationType relation;

        public PersonRelationKey(AbstractPerson person, PersonRelationType relation) {
            this.person = person;
            this.relation = relation;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof PersonRelationKey) {
                PersonRelationKey other = (PersonRelationKey) obj;
                return this.person == other.person && this.relation == other.relation;
            }
            return false;
        }

        public AbstractPerson getPerson() {
            return person;
        }

        public PersonRelationType getRelation() {
            return relation;
        }
    }

    public static class PersonRelation {
        private final PersonRelationKey key;
        private final AbstractPerson person;

        public PersonRelation(PersonRelationKey key, AbstractPerson person) {
            this.key = key;
            this.person = person;
        }

        public PersonRelationKey getKey() {
            return key;
        }

        public AbstractPerson getPerson() {
            return person;
        }
    }

    public static class PersonRelationStorage {
        private final List<PersonRelation> relations;

        public PersonRelationStorage(List<PersonRelation> relations) {
            this.relations = relations;
        }

        public void add(PersonRelation relation) {
            relations.add(relation);
        }

        public void remove(PersonRelation relation) {
            relations.remove(relation);
        }

        public void assignWaiter(Customer customer, Waiter waiter) {
            PersonRelationKey key = new PersonRelationKey(customer, PersonRelationType.CUSTOMER_WAITER);
            PersonRelation relation = new PersonRelation(key, waiter);
            add(relation);

            key = new PersonRelationKey(waiter, PersonRelationType.WAITER_CUSTOMER);
            relation = new PersonRelation(key, customer);
            add(relation);
        }

        public void assignChef(Customer customer, Chef chef) {
            PersonRelationKey key = new PersonRelationKey(customer, PersonRelationType.CUSTOMER_CHEF);
            PersonRelation relation = new PersonRelation(key, chef);
            add(relation);

            key = new PersonRelationKey(chef, PersonRelationType.CHEF_CUSTOMER);
            relation = new PersonRelation(key, customer);
            add(relation);
        }

        public void assignChef(Waiter waiter, Chef chef) {
            PersonRelationKey key = new PersonRelationKey(waiter, PersonRelationType.WAITER_CHEF);
            PersonRelation relation = new PersonRelation(key, chef);
            add(relation);

            key = new PersonRelationKey(chef, PersonRelationType.CHEF_WAITER);
            relation = new PersonRelation(key, waiter);
            add(relation);
        }

        public Waiter getWaiter(Customer customer) {
            PersonRelationKey key = new PersonRelationKey(customer, PersonRelationType.CUSTOMER_WAITER);
            for (PersonRelation relation : relations) {
                if (relation.getKey().equals(key)) {
                    return (Waiter) relation.getPerson();
                }
            }
            return null;
        }

        public Chef getChef(Customer customer) {
            PersonRelationKey key = new PersonRelationKey(customer, PersonRelationType.CUSTOMER_CHEF);
            for (PersonRelation relation : relations) {
                if (relation.getKey().equals(key)) {
                    return (Chef) relation.getPerson();
                }
            }
            return null;
        }

        public Chef getChef(Waiter waiter) {
            PersonRelationKey key = new PersonRelationKey(waiter, PersonRelationType.WAITER_CHEF);
            for (PersonRelation relation : relations) {
                if (relation.getKey().equals(key)) {
                    return (Chef) relation.getPerson();
                }
            }
            return null;
        }

        public Waiter getWaiter(Chef chef) {
            PersonRelationKey key = new PersonRelationKey(chef, PersonRelationType.CHEF_WAITER);
            for (PersonRelation relation : relations) {
                if (relation.getKey().equals(key)) {
                    return (Waiter) relation.getPerson();
                }
            }
            return null;
        }

        public Customer getCustomer(Waiter waiter) {
            PersonRelationKey key = new PersonRelationKey(waiter, PersonRelationType.WAITER_CUSTOMER);
            for (PersonRelation relation : relations) {
                if (relation.getKey().equals(key)) {
                    return (Customer) relation.getPerson();
                }
            }
            return null;
        }

        public Customer getCustomer(Chef chef) {
            PersonRelationKey key = new PersonRelationKey(chef, PersonRelationType.CHEF_CUSTOMER);
            for (PersonRelation relation : relations) {
                if (relation.getKey().equals(key)) {
                    return (Customer) relation.getPerson();
                }
            }
            return null;
        }

        public void remove(Customer customer) {
            PersonRelationKey key = new PersonRelationKey(customer, PersonRelationType.CUSTOMER_WAITER);
            PersonRelation relation = null;
            for (PersonRelation r : relations) {
                if (r.getKey().equals(key)) {
                    relation = r;
                    break;
                }
            }
            if (relation != null) {
                remove(relation);
            }

            key = new PersonRelationKey(customer, PersonRelationType.CUSTOMER_CHEF);
            relation = null;
            for (PersonRelation r : relations) {
                if (r.getKey().equals(key)) {
                    relation = r;
                    break;
                }
            }
            if (relation != null) {
                remove(relation);
            }
        }

    }
}
