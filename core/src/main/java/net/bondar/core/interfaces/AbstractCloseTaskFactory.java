package net.bondar.core.interfaces;


import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface for creating closing tasks.
 */
public interface AbstractCloseTaskFactory {

    /**
     * Creates closing task.
     *
     * @param interrupt       flag for interrupting working threads
     * @param disableStatInfo flag for disabling statistical information
     * @param processor       file processor
     * @param parameterHolder parameter holder
     * @return concrete closable instance
     */
    ICloseTask createCloseTask(AtomicBoolean interrupt,
                               AtomicBoolean disableStatInfo,
                               IProcessor processor,
                               IConfigHolder parameterHolder);
}
