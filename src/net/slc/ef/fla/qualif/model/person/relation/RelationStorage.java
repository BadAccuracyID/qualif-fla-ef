package net.slc.ef.fla.qualif.model.person.relation;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.person.customer.Customer;
import net.slc.ef.fla.qualif.model.person.waiter.Waiter;

import java.util.*;

public class RelationStorage {

    private final Map<AbstractPerson, List<Relation<? extends AbstractPerson, ? extends AbstractPerson>>> relations;

    public RelationStorage() {
        this.relations = new HashMap<>();
    }

    public <A extends AbstractPerson, B extends AbstractPerson> void addRelation(Relation<A, B> relation) {
        this.actualAddRelation(relation);
        this.actualAddRelation(relation.reverse());
    }

    private <A extends AbstractPerson, B extends AbstractPerson> void actualAddRelation(Relation<A, B> relation) {
        relations.putIfAbsent(relation.getPerson1(), new ArrayList<>());
        relations.get(relation.getPerson1()).add(relation);
    }

    public <A extends AbstractPerson, B extends AbstractPerson> B getRelatedPerson(A person, Class<B> relatedPersonClass) {
        for (Relation<? extends AbstractPerson, ? extends AbstractPerson> relation : relations.getOrDefault(person, Collections.emptyList())) {
            if (relatedPersonClass.isInstance(relation.getPerson2())) {
                return relatedPersonClass.cast(relation.getPerson2());
            }
        }

        return null;
    }

    public Waiter getWaiter(Customer customer) {
        return getRelatedPerson(customer, Waiter.class);
    }

    public Customer getCustomer(Waiter waiter) {
        return getRelatedPerson(waiter, Customer.class);
    }

    public Chef getChef(Customer customer) {
        return getRelatedPerson(customer, Chef.class);
    }

    public Customer getCustomer(Chef chef) {
        return getRelatedPerson(chef, Customer.class);
    }

    public Chef getChef(Waiter waiter) {
        return getRelatedPerson(waiter, Chef.class);
    }

    public Waiter getWaiter(Chef chef) {
        return getRelatedPerson(chef, Waiter.class);
    }

    public void removeRelations(AbstractPerson person) {
        relations.remove(person);
        relations.values().forEach(relations -> relations.removeIf(relation -> relation.getPerson2().equals(person)));
    }
}
