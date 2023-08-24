package net.slc.ef.fla.qualif.model.person.server;

import net.slc.ef.fla.qualif.state.TickableState;

public class ServerFacade {

    private final Server server;

    public ServerFacade(Server server) {
        this.server = server;
    }

    public void switchState(TickableState state) {
        this.server.getState().onExit();

        this.server.setState(state);
        this.server.getState().onEnter();
    }
}
