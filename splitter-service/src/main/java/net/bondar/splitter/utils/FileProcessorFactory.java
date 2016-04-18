package net.bondar.splitter.utils;

import net.bondar.splitter.interfaces.*;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;

/**
 * Provides creating <code>FileProcessor</code>.
 */
public class FileProcessorFactory implements AbstractProcessorFactory {

    /**
     * Creates <code>FileProcessor</code> depending on the received parameters.
     *
     * @param fileDest    specified file destination
     * @param partSize    part-file size
     * @param paramHolder parameter holder
     * @param iFactory    iterator factory
     * @param tFactory    threadFactory
     * @param taskFactory task factory
     * @param statFactory statistic factory
     * @return <code>FileProcessor</code> instance
     * @see {@link AbstractProcessorFactory}
     */
    @Override
    public FileProcessor createProcessor(String fileDest, long partSize,
                                         IParameterHolder paramHolder,
                                         AbstractIteratorFactory iFactory,
                                         AbstractThreadFactory tFactory,
                                         AbstractTaskFactory taskFactory,
                                         AbstractStatisticFactory statFactory) {
        if (partSize == 0) {
            return new FileProcessor(fileDest, paramHolder, iFactory, tFactory, taskFactory, statFactory);
        } else {
            return new FileProcessor(fileDest, partSize, paramHolder, iFactory, tFactory, taskFactory, statFactory);
        }
    }
}
