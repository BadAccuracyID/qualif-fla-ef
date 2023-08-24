package net.slc.ef.fla.qualif.model.person.server;

import net.slc.ef.fla.qualif.model.person.Person;
import net.slc.ef.fla.qualif.model.restaurant.Restaurant;

public class Server extends Person {

    private final ServerFacade serverFacade;

    public Server(Restaurant restaurant, String name) {
        super(restaurant, name);
        this.serverFacade = new ServerFacade(this);
    }

    public ServerFacade getServerFacade() {
        return serverFacade;
    }

}
