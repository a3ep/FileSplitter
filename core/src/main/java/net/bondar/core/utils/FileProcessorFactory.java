package net.bondar.core.utils;

import net.bondar.core.interfaces.*;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides creating <code>FileProcessor</code>.
 */
public class FileProcessorFactory implements AbstractProcessorFactory {

    /**
     * Creates <code>FileProcessor</code> depending on the received parameters.
     *
     * @param fileDest         specified file destination
     * @param partSize         part-file size
     * @param parameterHolder  parameter holder
     * @param iteratorFactory  iterator factory
     * @param taskFactory      task factory
     * @param closeTaskFactory closing task factory
     * @param statisticFactory statistic factory
     * @return <code>FileProcessor</code> instance
     * @see {@link AbstractProcessorFactory}
     */
    @Override
    public FileProcessor createProcessor(String fileDest, long partSize, AtomicBoolean interrupt,
                                         IConfigHolder parameterHolder,
                                         AbstractIteratorFactory iteratorFactory,
                                         AbstractTaskFactory taskFactory,
                                         AbstractCloseTaskFactory closeTaskFactory,
                                         AbstractStatisticFactory statisticFactory) {
        if (partSize == 0) {
            return new FileProcessor(fileDest, interrupt, parameterHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticFactory);
        } else {
            return new FileProcessor(fileDest, partSize, interrupt, parameterHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticFactory);
        }
    }
}