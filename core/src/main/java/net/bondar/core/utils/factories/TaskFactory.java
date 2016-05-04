package net.bondar.core.utils.factories;

import net.bondar.core.tasks.MergeTask;
import net.bondar.core.tasks.SplitTask;
import net.bondar.core.interfaces.*;
import net.bondar.core.interfaces.Iterable;
import net.bondar.statistics.interfaces.IStatisticsService;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Creates file tasks.
 */
public class TaskFactory extends AbstractTaskFactory {

    /**
     * Creates task depending on the received parameters.
     *
     * @param commandName       current command
     * @param file              specified file
     * @param interrupt         interrupt flag
     * @param paramHolder       parameter holder
     * @param iterator          split iterator
     * @param statisticsService statistics service
     * @return concrete task instance
     * @see {@link AbstractTask}
     */
    @Override
    public ITask createTask(String commandName, File file, AtomicBoolean interrupt, IConfigHolder paramHolder, Iterable iterator, IStatisticsService statisticsService) {
        if (commandName.equalsIgnoreCase("split")) {
            return new SplitTask(file, interrupt, paramHolder, iterator, statisticsService);
        } else {
            return new MergeTask(file, interrupt, paramHolder, iterator, statisticsService);
        }
    }
}
