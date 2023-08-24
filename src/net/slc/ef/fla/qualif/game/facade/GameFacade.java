package net.slc.ef.fla.qualif.game.facade;

import net.slc.ef.fla.qualif.state.MasterState;
import net.slc.ef.fla.qualif.game.Game;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class GameFacade {

    private final Game game;
    private final Scanner scanner;

    public GameFacade(Game game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public void switchGameState(MasterState state) {
        // todo
    }

    public void addExecutor(ExecutorService executorService) {
        game.getMain().getMenu().getExecutorServices().add(executorService);
    }

    public void removeExecutor(ExecutorService executorService) {
        game.getMain().getMenu().getExecutorServices().remove(executorService);
    }
}
