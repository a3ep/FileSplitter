package net.bondar.core.utils;

import net.bondar.core.domain.CloseTask;
import net.bondar.core.interfaces.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.ICloseTask;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.statistics.interfaces.IStatisticService;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides creating application closable.
 */
public class ApplicationCloseTaskFactory implements AbstractCloseTaskFactory {

    /**
     * Creates concrete cleaning-closing task.
     *
     * @param interrupt        interrupt flag
     * @param processor        file processor
     * @param parameterHolder  parameter holder
     * @param statisticService statistic service
     * @return <code>CloseTask</code> instance
     */
    @Override
    public ICloseTask createCloseTask(AtomicBoolean interrupt,
                                      IProcessor processor,
                                      IConfigHolder parameterHolder,
                                      IStatisticService statisticService) {
        return new CloseTask(interrupt, processor, parameterHolder, statisticService);
    }
}
