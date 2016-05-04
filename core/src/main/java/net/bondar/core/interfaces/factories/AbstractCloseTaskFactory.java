package net.bondar.core.interfaces.factories;


import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.core.interfaces.tasks.ICloseTask;

/**
 * Interface for creating closing tasks.
 */
public interface AbstractCloseTaskFactory {

    /**
     * Creates closing task.
     *
     * @param processor       file processor
     * @param parameterHolder parameter holder
     * @param threadName      name of the threads that need to interrupt
     * @return concrete closable instance
     */
    ICloseTask createCloseTask(IProcessor processor, IConfigHolder parameterHolder, String threadName);
}
