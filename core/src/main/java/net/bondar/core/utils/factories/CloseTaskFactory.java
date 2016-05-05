package net.bondar.core.utils.factories;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.core.interfaces.factories.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.tasks.ICloseTask;
import net.bondar.core.tasks.CloseTask;

/**
 * Provides creating application closable.
 */
public class CloseTaskFactory implements AbstractCloseTaskFactory {

    /**
     * Creates concrete cleaning-closing task.
     *
     * @param processor    file processor
     * @param configHolder configuration holder
     * @param threadName   name of the threads that need to interrupt
     * @return <code>CloseTask</code> instance
     */
    @Override
    public ICloseTask createCloseTask(IProcessor processor, IConfigHolder configHolder, final String threadName) {
        return new CloseTask(processor, configHolder, threadName);
    }
}
