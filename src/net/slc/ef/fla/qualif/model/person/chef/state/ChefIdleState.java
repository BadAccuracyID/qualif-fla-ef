package net.slc.ef.fla.qualif.model.person.chef.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.person.chef.Chef;

public class ChefIdleState extends ChefState {

    public ChefIdleState(Chef chef) {
        super(chef);
    }

    @Override
    public String getName() {
        return "idle";
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onTick() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public AbstractPerson getServer() {
        return null;
    }
}
