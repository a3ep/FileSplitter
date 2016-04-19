package net.bondar.splitter.interfaces;

import net.bondar.statistics.interfaces.IStatisticService;

import java.io.File;

/**
 * Interface for creating tasks.
 */
public abstract class AbstractTaskFactory {

    /**
     * Creates split task on the basis of received parameters.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param iterator    split iterator
     * @param statService statistic service
     * @return <code>SplitTask</code> task instance
     */
    public abstract AbstractTask createSplitTask(File file, IProcessor processor, IParameterHolder paramHolder,
                                                 Iterable iterator, IStatisticService statService);

    /**
     * Creates merge task on the basis of received parameters.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param iterator    split iterator
     * @param statService statistic service
     * @return <code>MergeTask</code> instance
     */
    public abstract AbstractTask createMergeTask(File file, IProcessor processor, IParameterHolder paramHolder,
                                                 Iterable iterator, IStatisticService statService);

    /**
     * Creates clean task on the basis of received parameters.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param statService statistic service
     * @return <code>CleanTask</code> instance
     */
    public abstract AbstractTask createCleanTask(File file, IProcessor processor, IParameterHolder paramHolder, IStatisticService statService);
}
