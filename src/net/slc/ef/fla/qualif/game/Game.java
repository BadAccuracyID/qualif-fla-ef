package net.slc.ef.fla.qualif.game;

import net.slc.ef.fla.qualif.Main;
import net.slc.ef.fla.qualif.game.facade.GameFacade;

public class Game {

    private final Main main;
    private final GameFacade gameFacade;

    public Game(Main main) {
        this.main = main;
        this.gameFacade = new GameFacade(this);
    }

    public GameFacade getGameFacade() {
        return gameFacade;
    }

    public Main getMain() {
        return main;
    }
}
