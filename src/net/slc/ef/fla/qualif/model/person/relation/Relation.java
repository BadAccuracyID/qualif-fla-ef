package net.slc.ef.fla.qualif.model.person.relation;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;

public class Relation<A extends AbstractPerson, B extends AbstractPerson> {

    private final A person1;
    private final B person2;

    public Relation(A person1, B person2) {
        this.person1 = person1;
        this.person2 = person2;
    }

    public A getPerson1() {
        return person1;
    }

    public B getPerson2() {
        return person2;
    }

    public Relation<B, A> reverse() {
        return new Relation<>(person2, person1);
    }

}
