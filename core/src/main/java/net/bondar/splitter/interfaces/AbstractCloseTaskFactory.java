package net.bondar.splitter.interfaces;

import net.bondar.statistics.interfaces.IStatisticService;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface for creating closing tasks.
 */
public interface AbstractCloseTaskFactory {

    /**
     * Creates closing task.
     *
     * @param interrupt interrupt flag
     * @param processor file processor
     * @param parameterHolder parameter holder
     * @param statisticService statistic service
     * @return concrete closable instance
     */
    ICloseTask createClosable(AtomicBoolean interrupt,
                              IProcessor processor,
                              IParameterHolder parameterHolder,
                              IStatisticService statisticService);

    /**
     * Creates closing task.
     *
     * @param interrupt interrupt flag
     * @return concrete closable instance
     */
    ICloseTask createClosable(AtomicBoolean interrupt);
}
