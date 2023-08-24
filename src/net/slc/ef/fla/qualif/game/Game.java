package net.slc.ef.fla.qualif.game;

import net.slc.ef.fla.qualif.Main;
import net.slc.ef.fla.qualif.game.facade.GameFacade;
import net.slc.ef.fla.qualif.game.state.GameStartingState;
import net.slc.ef.fla.qualif.game.state.GameState;

public class Game {

    private final Main main;
    private final GameFacade gameFacade;

    private GameState currentState;

    public Game(Main main) {
        this.main = main;
        this.gameFacade = new GameFacade(this);

        this.setCurrentState(new GameStartingState(this));
        this.getCurrentState().onEnter();
    }

    public GameFacade getGameFacade() {
        return gameFacade;
    }

    public Main getMain() {
        return main;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }
}
