package net.slc.ef.fla.qualif.menu.state;

import net.slc.ef.fla.qualif.menu.Menu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class MenuState {

    protected final Menu menu;
    protected ScheduledExecutorService executorService;

    protected MenuState(Menu menu) {
        this.menu = menu;
    }

    public abstract void onEnter();

    public abstract void onTick();

    public abstract void onExit();

    protected void switchState(MenuState state) {
        this.menu.getMenuFacade().switchMenuState(state);
    }

    protected void startTick(long delay, long period, TimeUnit timeUnit) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::onTick, delay, period, timeUnit);

        this.menu.getExecutorServices().add(executorService);
    }

    protected void stopTick() {
        if (executorService != null) {
            executorService.shutdownNow();
            this.menu.getExecutorServices().remove(executorService);
        }
    }
}
