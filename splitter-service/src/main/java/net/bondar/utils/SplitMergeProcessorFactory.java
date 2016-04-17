package net.bondar.utils;

import net.bondar.interfaces.*;

/**
 *
 */
public class SplitMergeProcessorFactory implements AbstractProcessorFactory {

    /**
     *
     * @param fileDest
     * @param partSize
     * @param parameterHolder
     * @param iteratorFactory
     * @param threadFactory
     * @param runnableFactory
     * @param statisticFactory
     * @return
     */
    @Override
    public IProcessor createProcessor(String fileDest, long partSize,
                                      IParameterHolder parameterHolder,
                                      AbstractIteratorFactory iteratorFactory,
                                      AbstractThreadFactory threadFactory,
                                      AbstractTaskFactory runnableFactory,
                                      AbstractStatisticFactory statisticFactory) {
        if (partSize == 0) {
            return new MergeProcessor(fileDest, parameterHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
        } else {
            return new SplitProcessor(fileDest, partSize, parameterHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
        }
    }
}
