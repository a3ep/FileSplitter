package net.bondar.splitter.utils;

import net.bondar.splitter.interfaces.*;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;

/**
 * Creates <code>SplitProcessor</code> or <code>MergeProcessor</code>.
 */
public class SplitMergeProcessorFactory implements AbstractProcessorFactory {

    /**
     * Creates <code>SplitProcessor</code> or <code>MergeProcessor</code> depending on the received parameters.
     *
     * @param fileDest    specified file destination
     * @param partSize    part-file size
     * @param paramHolder parameter holder
     * @param iFactory    iterator factory
     * @param tFactory    threadFactory
     * @param taskFactory task factory
     * @param statFactory statistic factory
     * @return <code>SplitProcessor</code> or <code>MergeProcessor</code> instance
     * @see {@link AbstractProcessorFactory}
     */
    @Override
    public IProcessor createProcessor(String fileDest, long partSize,
                                      IParameterHolder paramHolder,
                                      AbstractIteratorFactory iFactory,
                                      AbstractThreadFactory tFactory,
                                      AbstractTaskFactory taskFactory,
                                      AbstractStatisticFactory statFactory) {
        if (partSize == 0) {
            return new MergeProcessor(fileDest, paramHolder, iFactory, tFactory, taskFactory, statFactory);
        } else {
            return new SplitProcessor(fileDest, partSize, paramHolder, iFactory, tFactory, taskFactory, statFactory);
        }
    }
}
