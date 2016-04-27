package net.bondar.core.interfaces;


import net.bondar.statistics.interfaces.IStatisticsService;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface for creating tasks.
 */
public abstract class AbstractTaskFactory {

    /**
     * Creates split task on the basis of received parameters.
     *
     * @param command           current command
     * @param file              specified file
     * @param interrupt         interrupt flag
     * @param paramHolder       parameter holder
     * @param iterator          split iterator
     * @param statisticsService statistics service
     * @return concrete task instance
     */
    public abstract ITask createTask(String command, File file, AtomicBoolean interrupt, IConfigHolder paramHolder,
                                     Iterable iterator, IStatisticsService statisticsService);
}
