package net.slc.ef.fla.qualif.game.state;

import net.slc.ef.fla.qualif.game.Game;
import net.slc.ef.fla.qualif.state.TickableState;

import java.util.concurrent.ExecutorService;

public abstract class GameState extends TickableState {

    protected final Game game;

    protected GameState(Game game) {
        this.game = game;
    }

    @Override
    protected void switchState(TickableState state) {
        this.game.getGameFacade().switchGameState(state);
    }

    @Override
    protected void addExecutor(ExecutorService executor) {
        this.game.getGameFacade().addExecutor(executor);
    }

    @Override
    protected void removeExecutor(ExecutorService executor) {
        this.game.getGameFacade().removeExecutor(executor);
    }
}
