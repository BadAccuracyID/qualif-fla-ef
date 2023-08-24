package net.slc.ef.fla.qualif.model.person.server;

import net.slc.ef.fla.qualif.model.person.Person;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;
import net.slc.ef.fla.qualif.state.TickableState;

public class Server extends Person {

    private final ServerFacade serverFacade;
    private TickableState state;

    public Server(Restaurant restaurant, String name) {
        super(restaurant, name);
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
