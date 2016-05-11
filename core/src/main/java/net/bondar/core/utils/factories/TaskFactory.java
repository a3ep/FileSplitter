package net.bondar.core.utils.factories;

import net.bondar.core.interfaces.Iterable;
import net.bondar.core.interfaces.tasks.AbstractTask;
import net.bondar.core.interfaces.tasks.ITask;
import net.bondar.core.tasks.MergeTask;
import net.bondar.core.tasks.SplitTask;
import net.bondar.core.utils.ConfigHolder;
import net.bondar.statistics.interfaces.IStatisticsService;

import java.io.File;
import java.util.List;

/**
 * Creates file tasks.
 */
public class TaskFactory {

    /**
     * Creates task depending on the received parameters.
     *
     * @param file              specified file
     * @param files             specified list of part-files
     * @param configHolder      configuration holder
     * @param iterator          split iterator
     * @param statisticsService statistics service
     * @return concrete task instance
     * @see {@link AbstractTask}
     */
    public ITask createTask(final File file, List<File> files,
                            ConfigHolder configHolder,
                            Iterable iterator,
                            IStatisticsService statisticsService) {
        return files.isEmpty() ? new SplitTask(file, configHolder, iterator, statisticsService)
                : new MergeTask(files, configHolder, iterator, statisticsService);
    }
}
