package net.bondar.splitter.interfaces;

import net.bondar.splitter.utils.FileProcessor;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;

/**
 * Interface for creating concrete processors.
 */
public interface AbstractProcessorFactory {

    /**
     * Creates concrete processor on the basis of received parameters.
     *
     * @param fileDest    specified file destination
     * @param partSize    part-file size
     * @param paramHolder parameter holder
     * @param iFactory    iterator factory
     * @param taskFactory task factory
     * @param statFactory statistic factory
     * @return concrete processor instance
     */
    FileProcessor createProcessor(String fileDest, long partSize,
                                  IParameterHolder paramHolder,
                                  AbstractIteratorFactory iFactory,
                                  AbstractTaskFactory taskFactory,
                                  AbstractStatisticFactory statFactory);
}
