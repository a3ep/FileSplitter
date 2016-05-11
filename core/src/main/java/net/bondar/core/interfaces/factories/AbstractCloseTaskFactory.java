package net.bondar.core.interfaces.factories;


import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.core.interfaces.tasks.ICloseTask;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Provides creating closing tasks.
 */
public interface AbstractCloseTaskFactory {

    /**
     * Creates closing task.
     *
     * @param processor       file processor
     * @param futures         list of futures that represented threads in a pool
     * @return concrete closable instance
     */
    ICloseTask createCloseTask(final IProcessor processor, final List<Future> futures);
}
