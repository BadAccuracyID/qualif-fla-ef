package net.slc.ef.fla.qualif.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ASExecutorManager {

    private final List<ASExecutor> executors = new ArrayList<>();
    private final ScheduledExecutorService checkExecutor = Executors.newSingleThreadScheduledExecutor();

    public ASExecutorManager() {
        this.checkAll();
    }

    public void addExecutor(ASExecutor executor) {
        executors.add(executor);
    }

    public void removeExecutor(ASExecutor executor) {
        executors.remove(executor);
    }

    public void shutdown() {
        this.checkExecutor.shutdownNow();
        this.executors.forEach(ASExecutor::shutdown);
    }

    private void checkAll() {
        checkExecutor.scheduleAtFixedRate(() -> executors.forEach(ASExecutor::check), 0, 1, TimeUnit.SECONDS);
    }

}
