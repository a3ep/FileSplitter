package net.bondar.splitter.utils;

import net.bondar.splitter.domain.CleanCloseTask;
import net.bondar.splitter.domain.CloseTask;
import net.bondar.splitter.interfaces.AbstractCloseTaskFactory;
import net.bondar.splitter.interfaces.ICloseTask;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.interfaces.IProcessor;
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
     * @return <code>CleanCloseTask</code> instance
     */
    @Override
    public ICloseTask createClosable(AtomicBoolean interrupt,
                                     IProcessor processor,
                                     IParameterHolder parameterHolder,
                                     IStatisticService statisticService) {
        return new CleanCloseTask(interrupt, processor, parameterHolder, statisticService);
    }

    /**
     * Creates closing task.
     *
     * @param interrupt interrupt flag
     * @return <code>CloseTask</code>  instance
     */
    @Override
    public ICloseTask createClosable(AtomicBoolean interrupt) {
        return new CloseTask(interrupt);
    }
}
