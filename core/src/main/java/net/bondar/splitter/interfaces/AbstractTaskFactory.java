package net.bondar.splitter.interfaces;

import net.bondar.statistics.interfaces.IStatisticService;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface for creating tasks.
 */
public abstract class AbstractTaskFactory {

    /**
     * Creates split task on the basis of received parameters.
     *
     * @param file        specified file
     * @param interrupt   interrupt flag
     * @param paramHolder parameter holder
     * @param iterator    split iterator
     * @param statService statistic service
     * @return <code>SplitTask</code> task instance
     */
    public abstract ITask createSplitTask(File file, AtomicBoolean interrupt, IParameterHolder paramHolder,
                                                 Iterable iterator, IStatisticService statService);

    /**
     * Creates merge task on the basis of received parameters.
     *
     * @param file        specified file
     * @param interrupt   interrupt flag
     * @param paramHolder parameter holder
     * @param iterator    split iterator
     * @param statService statistic service
     * @return <code>MergeTask</code> instance
     */
    public abstract ITask createMergeTask(File file, AtomicBoolean interrupt, IParameterHolder paramHolder,
                                                 Iterable iterator, IStatisticService statService);
}
