package net.bondar.core.utils.factories;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.core.interfaces.factories.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.tasks.ICloseTask;
import net.bondar.core.tasks.CloseTask;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Provides creating application closable.
 */
public class CloseTaskFactory implements AbstractCloseTaskFactory {

    /**
     * Creates concrete cleaning-closing task.
     *
     * @param processor    file processor
     * @param configHolder configuration holder
     * @param futures      list of futures that represented threads in a pool
     * @return <code>CloseTask</code> instance
     */
    @Override
    public ICloseTask createCloseTask(IProcessor processor, IConfigHolder configHolder, final List<Future> futures) {
        return new CloseTask(processor, configHolder, futures);
    }
}
