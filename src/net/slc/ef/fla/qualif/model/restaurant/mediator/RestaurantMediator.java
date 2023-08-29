package net.slc.ef.fla.qualif.model.restaurant.mediator;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefCookState;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefIdleState;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.customer.state.CustomerEatState;
import net.slc.ef.fla.qualif.model.person.customer.state.CustomerOrderState;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;
import net.slc.ef.fla.qualif.model.person.waiter.state.WaiterIdleState;
import net.slc.ef.fla.qualif.model.person.waiter.state.WaiterServeState;
import net.slc.ef.fla.qualif.model.person.waiter.state.WaiterTakeOrderState;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.model.restaurant.RestaurantFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantMediator {

    private final RestaurantFacade restaurantFacade;
    private final PersonRelationStorage personRelationStorage;

    public RestaurantMediator(Restaurant restaurant) {
        this.restaurantFacade = restaurant.getRestaurantFacade();
        this.personRelationStorage = new PersonRelationStorage(new ArrayList<>());
    }

    public void notify(AbstractPerson sender, MediatorAction action) {
        switch (action) {
            case REQUEST_WAITER: { // customer wants to order
                Waiter availableWaiter = restaurantFacade.getIdlingWaiter();
                if (availableWaiter == null) {
                    return;
                }

                assert sender instanceof Customer;
                Customer customer = (Customer) sender;

                personRelationStorage.assignWaiter(customer, availableWaiter);

                customer.getCustomerFacade().switchState(new CustomerOrderState(customer));
                availableWaiter.getWaiterFacade().switchState(new WaiterTakeOrderState(availableWaiter));
                break;
            }

            case REQUEST_COOK: { // waiter forwards order to cook
                Chef availableChef = restaurantFacade.getIdlingChef();
                if (availableChef == null) {
                    return;
                }

                assert sender instanceof Waiter;
                Waiter waiter = (Waiter) sender;

                personRelationStorage.assignChef(waiter, availableChef);
                Customer customer = personRelationStorage.getCustomer(waiter);
                personRelationStorage.assignChef(customer, availableChef);

                waiter.getWaiterFacade().switchState(new WaiterIdleState(waiter));
                availableChef.getChefFacade().switchState(new ChefCookState(availableChef));
                break;
            }

            case DELIVER_TO_WAITER: { // delivering food from cook to waiter
                Waiter availableWaiter = restaurantFacade.getIdlingWaiter();
                if (availableWaiter == null) {
                    return;
                }

                assert sender instanceof Chef;
                Chef chef = (Chef) sender;

                Customer customer = personRelationStorage.getCustomer(chef);
                personRelationStorage.assignChef(customer, chef);
                personRelationStorage.assignWaiter(customer, availableWaiter);

                chef.getChefFacade().switchState(new ChefIdleState(chef));
                availableWaiter.getWaiterFacade().switchState(new WaiterServeState(availableWaiter));
                break;
            }

            case DELIVER_TO_CUSTOMER: { // delivering food from waiter to their customer
                assert sender instanceof Waiter;
                Waiter waiter = (Waiter) sender;

                Customer customer = personRelationStorage.getCustomer(waiter);
                customer.getCustomerFacade().switchState(new CustomerEatState(customer));
                break;
            }

            case CUSTOMER_LEAVE: {
                assert sender instanceof Customer;
                Customer customer = (Customer) sender;

                Chef chef = personRelationStorage.getChef(customer);

                restaurantFacade.removeCustomer(customer);
                restaurantFacade.addScore(30 * chef.getSkillLevel());

                personRelationStorage.remove(customer);
                break;
            }

            default: {
                throw new IllegalStateException("Unexpected value: " + action);
            }
        }
    }

    enum PersonRelationType {
        CUSTOMER_WAITER,
        CUSTOMER_CHEF,
        WAITER_CUSTOMER,
        WAITER_CHEF,
        CHEF_CUSTOMER,
        CHEF_WAITER
    }

    static class PersonRelationKey {
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

    static class PersonRelation {
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

    static class PersonRelationStorage {
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
