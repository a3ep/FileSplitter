package net.bondar.interfaces;

import java.io.File;

/**
 *
 */
public abstract class AbstractTaskFactory {

    /**
     *
     * @param file
     * @param parameterHolder
     * @param iterator
     * @param statisticService
     * @return
     */
    public abstract AbstractTask createSplitTask(File file, IParameterHolder parameterHolder,
                                                 Iterable iterator, IStatisticService statisticService);

    /**
     *
     * @param file
     * @param parameterHolder
     * @param iterator
     * @param statisticService
     * @return
     */
    public abstract AbstractTask createMergeTask(File file, IParameterHolder parameterHolder,
                                                 Iterable iterator, IStatisticService statisticService);


}
