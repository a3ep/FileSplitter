package net.bondar.core.utils.factories;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.factories.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.factories.AbstractIteratorFactory;
import net.bondar.core.interfaces.factories.AbstractProcessorFactory;
import net.bondar.core.interfaces.factories.AbstractTaskFactory;
import net.bondar.core.utils.FileProcessor;
import net.bondar.statistics.interfaces.IStatisticsService;

/**
 * Provides creating <code>FileProcessor</code>.
 */
public class ProcessorFactory implements AbstractProcessorFactory {

    /**
     * Creates <code>FileProcessor</code> depending on the received parameters.
     *
     * @param fileDest          specified file destination
     * @param partSize          part-file size
     * @param configHolder      configuration holder
     * @param iteratorFactory   iterator factory
     * @param taskFactory       task factory
     * @param closeTaskFactory  closing task factory
     * @param statisticsService statistics service
     * @param commandName       name of input command
     * @return <code>FileProcessor</code> instance
     * @see {@link AbstractProcessorFactory}
     */
    @Override
    public FileProcessor createProcessor(final String fileDest, final long partSize,
                                         IConfigHolder configHolder,
                                         AbstractIteratorFactory iteratorFactory,
                                         AbstractTaskFactory taskFactory,
                                         AbstractCloseTaskFactory closeTaskFactory,
                                         IStatisticsService statisticsService,
                                         final String commandName) {
        if (partSize == 0) {
            return new FileProcessor(fileDest, configHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService, commandName);
        } else {
            return new FileProcessor(fileDest, partSize, configHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService, commandName);
        }
    }
}
