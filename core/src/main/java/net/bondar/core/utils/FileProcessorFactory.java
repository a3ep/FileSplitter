package net.bondar.core.utils;

import net.bondar.core.interfaces.*;
import net.bondar.statistics.interfaces.IStatisticService;

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
     * @param statisticService statistic service
     * @param commandName      name of input command
     * @return <code>FileProcessor</code> instance
     * @see {@link AbstractProcessorFactory}
     */
    @Override
    public FileProcessor createProcessor(String fileDest, long partSize,
                                         IConfigHolder parameterHolder,
                                         AbstractIteratorFactory iteratorFactory,
                                         AbstractTaskFactory taskFactory,
                                         AbstractCloseTaskFactory closeTaskFactory,
                                         IStatisticService statisticService,
                                         String commandName) {
        if (partSize == 0) {
            return new FileProcessor(fileDest, parameterHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticService, commandName);
        } else {
            return new FileProcessor(fileDest, partSize, parameterHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticService, commandName);
        }
    }
}
