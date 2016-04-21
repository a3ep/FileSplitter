package net.bondar.splitter.interfaces;

import net.bondar.splitter.utils.FileProcessor;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface for creating concrete processors.
 */
public interface AbstractProcessorFactory {

    /**
     * Creates concrete processor on the basis of received parameters.
     *
     * @param fileDest         specified file destination
     * @param partSize         part-file size
     * @param interrupt        interrupt flag
     * @param parameterHolder  parameter holder
     * @param iteratorFactory  iterator factory
     * @param taskFactory      task factory
     * @param closeTaskFactory close task factory
     * @param statisticFactory statistic factory
     * @return concrete processor instance
     */
    FileProcessor createProcessor(String fileDest, long partSize, AtomicBoolean interrupt,
                                  IParameterHolder parameterHolder,
                                  AbstractIteratorFactory iteratorFactory,
                                  AbstractTaskFactory taskFactory,
                                  AbstractCloseTaskFactory closeTaskFactory,
                                  AbstractStatisticFactory statisticFactory);
}
