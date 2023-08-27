package net.slc.ef.fla.qualif.model.person.cook;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.PersonFactory;
import net.slc.ef.fla.qualif.model.person.PersonInitialGenerator;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class CookFactory implements PersonFactory {

    private final Restaurant restaurant;
    private final PersonInitialGenerator initialsGenerator;

    public CookFactory(Restaurant restaurant, PersonInitialGenerator initialsGenerator) {
        this.restaurant = restaurant;
        this.initialsGenerator = initialsGenerator;
    }

    @Override
    public AbstractPerson create() {
        String initial = initialsGenerator.generateInitial();
        return new Cook(restaurant, initial);
    }


}
