package net.slc.ef.fla.qualif.model.person.chef;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.state.ChefIdleState;
import net.slc.ef.fla.qualif.model.person.state.ServableState;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class Chef extends AbstractPerson {

    private final ChefFacade chefFacade;
    private ServableState state;

    private int speed;
    private int skillLevel;

    public Chef(Restaurant restaurant, String name) {
        super(restaurant, name);
        this.chefFacade = new ChefFacade(this);

        this.state = new ChefIdleState(this);
        this.state.onEnter();

        this.speed = 1;
        this.skillLevel = 1;
    }

    @Override
    public void tick() {
        this.state.onTick();
    }

    public ChefFacade getChefFacade() {
        return chefFacade;
    }

    public ServableState getState() {
        return state;
    }

    public void setState(ServableState state) {
        this.state = state;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
