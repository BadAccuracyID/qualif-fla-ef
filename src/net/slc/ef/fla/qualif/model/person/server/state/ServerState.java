package net.slc.ef.fla.qualif.model.person.server.state;

import net.slc.ef.fla.qualif.model.person.server.Server;
import net.slc.ef.fla.qualif.state.TickableState;

import java.util.concurrent.ExecutorService;

public abstract class ServerState extends TickableState {

    protected final Server server;

    public ServerState(Server server) {
        this.server = server;
    }

    @Override
    protected void switchState(TickableState state) {
        this.server.getServerFacade().switchState(state);
    }

    @Override
    protected void addExecutor(ExecutorService executor) {
        this.server.getRestaurant().getRestaurantFacade().addExecutor(executor);
    }

    @Override
    protected void removeExecutor(ExecutorService executor) {
        this.server.getRestaurant().getRestaurantFacade().removeExecutor(executor);
    }
}
