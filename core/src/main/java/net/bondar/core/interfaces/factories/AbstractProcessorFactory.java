package net.bondar.core.interfaces.factories;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.utils.FileProcessor;
import net.bondar.statistics.interfaces.IStatisticsService;

/**
 * Provides creating concrete processors.
 */
public interface AbstractProcessorFactory {

    /**
     * Creates concrete processor on the basis of received parameters.
     *
     * @param fileDest          specified file destination
     * @param partSize          part-file size
     * @param parameterHolder   parameter holder
     * @param iteratorFactory   iterator factory
     * @param taskFactory       task factory
     * @param closeTaskFactory  close task factory
     * @param statisticsService statistics service
     * @param commandName       name of input command
     * @return concrete processor instance
     */
    FileProcessor createProcessor(final String fileDest, final long partSize,
                                  IConfigHolder parameterHolder,
                                  AbstractIteratorFactory iteratorFactory,
                                  AbstractTaskFactory taskFactory,
                                  AbstractCloseTaskFactory closeTaskFactory,
                                  IStatisticsService statisticsService,
                                  final String commandName);
}
