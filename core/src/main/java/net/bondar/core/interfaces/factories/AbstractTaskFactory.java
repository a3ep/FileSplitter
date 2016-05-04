package net.bondar.core.interfaces.factories;


import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.interfaces.tasks.ITask;
import net.bondar.statistics.interfaces.IStatisticsService;

import java.io.File;

/**
 * Interface for creating tasks.
 */
public abstract class AbstractTaskFactory {

    /**
     * Creates split task on the basis of received parameters.
     *
     * @param command           current command
     * @param file              specified file
     * @param paramHolder       parameter holder
     * @param iterator          split iterator
     * @param statisticsService statistics service
     * @return concrete task instance
     */
    public abstract ITask createTask(String command, File file, IConfigHolder paramHolder,
                                     Iterable iterator, IStatisticsService statisticsService);
}
