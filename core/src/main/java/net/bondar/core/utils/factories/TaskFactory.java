package net.bondar.core.utils.factories;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.interfaces.factories.AbstractTaskFactory;
import net.bondar.core.interfaces.tasks.AbstractTask;
import net.bondar.core.interfaces.tasks.ITask;
import net.bondar.core.tasks.MergeTask;
import net.bondar.core.tasks.SplitTask;
import net.bondar.statistics.interfaces.IStatisticsService;

import java.io.File;

/**
 * Creates file tasks.
 */
public class TaskFactory extends AbstractTaskFactory {

    /**
     * Creates task depending on the received parameters.
     *
     * @param commandName       current command
     * @param file              specified file
     * @param configHolder      configuration holder
     * @param iterator          split iterator
     * @param statisticsService statistics service
     * @return concrete task instance
     * @see {@link AbstractTask}
     */
    @Override
    public ITask createTask(final String commandName,
                            final File file,
                            IConfigHolder configHolder,
                            Iterable iterator,
                            IStatisticsService statisticsService) {
        if (commandName.equalsIgnoreCase("split")) {
            return new SplitTask(file, configHolder, iterator, statisticsService);
        } else {
            return new MergeTask(file, configHolder, iterator, statisticsService);
        }
    }
}
