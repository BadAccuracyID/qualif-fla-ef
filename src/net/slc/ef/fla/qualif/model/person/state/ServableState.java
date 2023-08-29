package net.slc.ef.fla.qualif.model.person.state;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.state.TickableState;

public abstract class ServableState extends TickableState {

    public abstract AbstractPerson getServer();

    @Override
    protected void switchState(TickableState state) {
        this.switchState((ServableState) state);
    }

    protected abstract void switchState(ServableState state);

}
