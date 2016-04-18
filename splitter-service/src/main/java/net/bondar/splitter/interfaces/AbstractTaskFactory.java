package net.bondar.splitter.interfaces;

import net.bondar.statistics.interfaces.IStatisticService;

import java.io.File;

/**
 * Interface for creating split or merge tasks.
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
    public abstract AbstractTask createSplitTask(File file, IParameterHolder paramHolder,
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
    public abstract AbstractTask createMergeTask(File file, IParameterHolder paramHolder,
                                                 Iterable iterator, IStatisticService statService);
}