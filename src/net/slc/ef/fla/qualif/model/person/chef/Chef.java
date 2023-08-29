package net.slc.ef.fla.qualif.model.person.chef;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Chef extends AbstractPerson {

    private final ChefFacade chefFacade;
    private TickableState state;

    private int speed;
    private int skillLevel;

    public Chef(Restaurant restaurant, String name) {
        super(restaurant, name);
        this.chefFacade = new ChefFacade(this);
    }

    @Override
    public void tick() {

    }

    public ChefFacade getChefFacade() {
        return chefFacade;
    }

    public TickableState getState() {
        return state;
    }

    public void setState(TickableState state) {
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
