package net.bondar.splitter.utils;

import net.bondar.splitter.domain.CleanTask;
import net.bondar.splitter.domain.MergeTask;
import net.bondar.splitter.domain.SplitTask;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.interfaces.Iterable;
import net.bondar.statistics.interfaces.IStatisticService;

import java.io.File;

/**
 * Creates <code>SplitTask</code> or <code>MergeTask</code>.
 */
public class FileTaskFactory extends AbstractTaskFactory {

    /**
     * Creates <code>SplitTask</code> depending on the received parameters.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param iterator    split iterator
     * @param statService statistic service
     * @return <code>SplitTask</code> task instance
     * @see {@link AbstractTask}
     */
    @Override
    public AbstractTask createSplitTask(File file, IProcessor processor, IParameterHolder paramHolder, Iterable iterator, IStatisticService statService) {
        return new SplitTask(file, processor, paramHolder, iterator, statService);
    }

    /**
     * Creates <code>MergeTask</code> depending on the received parameters.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param iterator    merge iterator
     * @param statService statistic service
     * @return <code>MergeTask</code> task instance
     * @see {@link AbstractTask}
     */
    @Override
    public AbstractTask createMergeTask(File file, IProcessor processor, IParameterHolder paramHolder, Iterable iterator, IStatisticService statService) {
        return new MergeTask(file, processor, paramHolder, iterator, statService);
    }

    /**
     * Creates <code>CleanTask</code> depending on the received parameters.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param statService statistic service
     * @return <code>CleanTask</code> task instance
     * @see {@link AbstractTask}
     */
    @Override
    public AbstractTask createCleanTask(File file, IProcessor processor, IParameterHolder paramHolder, IStatisticService statService) {
        return new CleanTask(file, processor, paramHolder, statService);
    }
}
