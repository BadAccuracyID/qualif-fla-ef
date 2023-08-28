package net.slc.ef.fla.qualif.state;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class TickableState extends MasterState {
    protected ScheduledExecutorService executorService;

    public abstract void onTick();

    @Override
    protected void switchState(MasterState state) {
        this.switchState((TickableState) state);
    }

    protected abstract void switchState(TickableState state);

    protected abstract void addExecutor(ExecutorService executor);

    protected abstract void removeExecutor(ExecutorService executor);

    protected void startTick(long delay, long period, TimeUnit timeUnit) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::onTick, delay, period, timeUnit);

        this.addExecutor(executorService);
    }

    protected void stopTick() {
        if (executorService != null) {
            executorService.shutdownNow();
            this.removeExecutor(executorService);
        }
    }
}
