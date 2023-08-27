package net.slc.ef.fla.qualif.model.person.server;

import net.slc.ef.fla.qualif.model.person.AbstractPerson;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Server extends AbstractPerson {

    private final ServerFacade serverFacade;
    private TickableState state;

    public Server(Restaurant restaurant, String initial) {
        super(restaurant, initial);
        this.serverFacade = new ServerFacade(this);
    }

    public TickableState getState() {
        return state;
    }

    public void setState(TickableState state) {
        this.state = state;
    }

    public ServerFacade getServerFacade() {
        return serverFacade;
    }

}
