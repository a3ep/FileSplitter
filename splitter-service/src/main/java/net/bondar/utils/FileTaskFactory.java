package net.bondar.utils;

import net.bondar.domain.MergeTask;
import net.bondar.domain.SplitTask;
import net.bondar.interfaces.*;
import net.bondar.interfaces.Iterable;

import java.io.File;

/**
 *
 */
public class FileTaskFactory extends AbstractTaskFactory {

    /**
     *
     * @param file
     * @param parameterHolder
     * @param iterator
     * @param statisticService
     * @return
     */
    @Override
    public AbstractTask createSplitTask(File file, IParameterHolder parameterHolder, Iterable iterator, IStatisticService statisticService) {
        return new SplitTask(file, parameterHolder, iterator, statisticService);
    }

    /**
     *
     * @param file
     * @param parameterHolder
     * @param iterator
     * @param statisticService
     * @return
     */
    @Override
    public AbstractTask createMergeTask(File file, IParameterHolder parameterHolder, Iterable iterator, IStatisticService statisticService) {
        return new MergeTask(file, parameterHolder, iterator, statisticService);
    }
}
