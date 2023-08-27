package net.slc.ef.fla.qualif.async;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ASExecutor {

    private final Supplier<ScheduledExecutorService> executorSupplier;
    private final Runnable runnable;
    private final long delay;
    private final long period;
    private final TimeUnit timeUnit;
    private Predicate<Void> runningCondition;
    private ScheduledExecutorService executor;

    private ASExecutor(Supplier<ScheduledExecutorService> executorSupplier, Runnable runnable, long delay, long period, TimeUnit timeUnit, Predicate<Void> runningCondition) {
        this.executorSupplier = executorSupplier;
        this.runnable = runnable;
        this.delay = delay;
        this.period = period;
        this.timeUnit = timeUnit;
        this.runningCondition = runningCondition;
        this.executor = null;
    }

    public static ASExecutorBuilder builder() {
        return new ASExecutorBuilder();
    }

    public void setRunningCondition(Predicate<Void> runningCondition) {
        this.runningCondition = runningCondition;
    }

    public void check() {
        if (runningCondition != null && runningCondition.test(null)) {
            if (executor == null) {
                executor = executorSupplier.get();
                executor.scheduleAtFixedRate(runnable, delay, period, timeUnit);
            }
        } else {
            if (executor != null) {
                executor.shutdown();
                executor = null;
            }
        }
    }

    public void shutdown() {
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
    }

    public static class ASExecutorBuilder {
        private Supplier<ScheduledExecutorService> executor;
        private Runnable runnable;
        private long delay = 0;
        private long period = 1;
        private TimeUnit timeUnit = TimeUnit.SECONDS;

        private Predicate<Void> runningCondition;

        public ASExecutorBuilder executor(Supplier<ScheduledExecutorService> executor) {
            this.executor = executor;
            return this;
        }

        public ASExecutorBuilder task(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public ASExecutorBuilder delay(long delay) {
            this.delay = delay;
            return this;
        }

        public ASExecutorBuilder period(long period) {
            this.period = period;
            return this;
        }

        public ASExecutorBuilder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public ASExecutorBuilder runningCondition(Predicate<Void> runningCondition) {
            this.runningCondition = runningCondition;
            return this;
        }

        public ASExecutor build() {
            return new ASExecutor(executor, runnable, delay, period, timeUnit, runningCondition);
        }
    }

}
