package net.bondar.core.utils.factories;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.factories.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.factories.AbstractIteratorFactory;
import net.bondar.core.interfaces.factories.AbstractProcessorFactory;
import net.bondar.core.interfaces.factories.AbstractTaskFactory;
import net.bondar.core.utils.FileSplitterProcessor;
import net.bondar.statistics.interfaces.IStatisticsService;

/**
 * Provides creating <code>FileSplitterProcessor</code>.
 */
public class ProcessorFactory implements AbstractProcessorFactory {

    /**
     * Creates <code>FileSplitterProcessor</code> depending on the received parameters.
     *
     * @param fileDest          specified file destination
     * @param partSize          part-file size
     * @param configHolder      configuration holder
     * @param iteratorFactory   iterator factory
     * @param taskFactory       task factory
     * @param closeTaskFactory  closing task factory
     * @param statisticsService statistics service
     * @param commandName       name of input command
     * @return <code>FileSplitterProcessor</code> instance
     * @see {@link AbstractProcessorFactory}
     */
    @Override
    public FileSplitterProcessor createProcessor(String fileDest, long partSize,
                                                 IConfigHolder configHolder,
                                                 AbstractIteratorFactory iteratorFactory,
                                                 AbstractTaskFactory taskFactory,
                                                 AbstractCloseTaskFactory closeTaskFactory,
                                                 IStatisticsService statisticsService,
                                                 String commandName) {
        if (partSize == 0) {
            return new FileSplitterProcessor(fileDest, configHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService, commandName);
        } else {
            return new FileSplitterProcessor(fileDest, partSize, configHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService, commandName);
        }
    }
}
