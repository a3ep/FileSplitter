package net.bondar.core.utils;

import net.bondar.core.domain.CloseTask;
import net.bondar.core.interfaces.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.ICloseTask;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides creating application closable.
 */
public class ApplicationCloseTaskFactory implements AbstractCloseTaskFactory {

    /**
     * Creates concrete cleaning-closing task.
     *
     * @param interrupt       flag for interrupting working threads
     * @param disableStatInfo flag for disabling statistical information
     * @param processor       file processor
     * @param parameterHolder parameter holder
     * @return <code>CloseTask</code> instance
     */
    @Override
    public ICloseTask createCloseTask(AtomicBoolean interrupt,
                                      AtomicBoolean disableStatInfo,
                                      IProcessor processor,
                                      IConfigHolder parameterHolder) {
        return new CloseTask(interrupt, disableStatInfo, processor, parameterHolder);
    }
}
