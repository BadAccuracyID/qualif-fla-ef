package net.slc.ef.fla.qualif.game.state;

import net.slc.ef.fla.qualif.state.MasterState;
import net.slc.ef.fla.qualif.game.Game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class GameState extends MasterState {

    protected final Game game;
    protected ScheduledExecutorService executorService;

    protected GameState(Game game) {
        this.game = game;
    }

    @Override
    protected void switchState(MasterState state) {
        this.game.getGameFacade().switchGameState(state);
    }

    protected void startTick(long delay, long period, TimeUnit timeUnit) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::onTick, delay, period, timeUnit);

        this.game.getGameFacade().addExecutor(executorService);
    }

    protected void stopTick() {
        if (executorService != null) {
            executorService.shutdownNow();
            this.game.getGameFacade().removeExecutor(executorService);
        }
    }
}
