package net.bondar.core.interfaces;

import net.bondar.core.utils.FileProcessor;
import net.bondar.statistics.interfaces.IStatisticService;

/**
 * Interface for creating concrete processors.
 */
public interface AbstractProcessorFactory {

    /**
     * Creates concrete processor on the basis of received parameters.
     *
     * @param fileDest         specified file destination
     * @param partSize         part-file size
     * @param parameterHolder  parameter holder
     * @param iteratorFactory  iterator factory
     * @param taskFactory      task factory
     * @param closeTaskFactory close task factory
     * @param statisticService statistic service
     * @param commandName      name of input command
     * @return concrete processor instance
     */
    FileProcessor createProcessor(String fileDest, long partSize,
                                  IConfigHolder parameterHolder,
                                  AbstractIteratorFactory iteratorFactory,
                                  AbstractTaskFactory taskFactory,
                                  AbstractCloseTaskFactory closeTaskFactory,
                                  IStatisticService statisticService, String commandName);
}
