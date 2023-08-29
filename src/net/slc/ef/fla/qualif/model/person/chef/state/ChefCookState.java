package net.slc.ef.fla.qualif.model.person.chef.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.Chef;
import net.slc.ef.fla.qualif.model.restaurant.mediator.MediatorAction;
import net.slc.ef.fla.qualif.model.restaurant.mediator.RestaurantMediator;

public class ChefCookState extends ChefState {

    private int delay;

    public ChefCookState(Chef chef) {
        super(chef);
    }

    @Override
    public String getName() {
        return "cook";
    }

    @Override
    public void onEnter() {
        this.delay = 6 - chef.getSpeed();
    }

    @Override
    public void onTick() {
        this.delay--;
        if (this.delay <= 0) {
            chef.getRestaurant().getRestaurantMediator().notify(chef, MediatorAction.CHEF_COOK_DONE);
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        RestaurantMediator.PersonRelationStorage relationStorage = chef.getRestaurant().getRestaurantMediator().getRelationStorage();
        return relationStorage.getCustomer(chef);
    }
}
