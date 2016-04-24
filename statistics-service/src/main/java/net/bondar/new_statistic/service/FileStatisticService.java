package net.bondar.new_statistic.service;

import net.bondar.new_statistic.exceptions.StatisticException;
import net.bondar.new_statistic.interfaces.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Provides working with statistical information.
 */
public class FileStatisticService implements IStatisticService {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Statistic holder.
     */
    private final IStatisticHolder holder;

    /**
     * Statistic parser.
     */
    private final IStatisticParser parser;

    /**
     * Statistic converter.
     */
    private final IStatisticConverter converter;

    /**
     * Statistic builder.
     */
    private final IStatisticBuilder builder;

    /**
     * Statistic viewer.
     */
    private final IStatisticViewer viewer;

    /**
     * Timer.
     */
    private final Timer timer;

    /**
     * Creates <code>FileStatisticService</code> instance.
     *
     * @param holder    statistic holder
     * @param parser    statistic parser
     * @param converter statistic converter
     * @param builder   statistic builder
     * @param viewer    statistic viewer
     * @param timer     timer
     */
    public FileStatisticService(IStatisticHolder holder,
                                IStatisticParser parser,
                                IStatisticConverter converter,
                                IStatisticBuilder builder,
                                IStatisticViewer viewer,
                                Timer timer) {
        this.holder = holder;
        this.parser = parser;
        this.converter = converter;
        this.builder = builder;
        this.viewer = viewer;
        this.timer = timer;
    }

    /**
     * Starts showing statistical information about file processing to user.
     *
     * @param delay  delay in milliseconds before task is to be executed.
     * @param period time in milliseconds between successive task executions.
     * @throws IllegalArgumentException if {@code delay < 0}, or
     *                                  {@code delay + System.currentTimeMillis() < 0}, or
     *                                  {@code period <= 0}
     * @throws IllegalStateException    if task was already scheduled or
     *                                  cancelled, timer was cancelled, or timer thread terminated.
     * @throws NullPointerException     if {@code task} is null
     * @see {@link IStatisticService}, {@link Timer}
     */
    @Override
    public void showStatisticalInfo(int delay, int period) throws IllegalArgumentException, IllegalStateException, NullPointerException{
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    viewer.showStatistic(builder.buildStatInfoString(converter.convert(parser.
                            parseStatisticalInfo(holder.getStatisticalInfo()))));
                }
            }, delay, period);
        }catch (StatisticException e){
            log.error("Statistic service error. Message: "+e.getMessage());
        }
    }

    /**
     * Ends showing statistical information about file processing to user.
     *
     * @see {@link IStatisticService}
     */
    @Override
    public void hideStatisticalInfo() {
        timer.cancel();
    }

    /**
     * Holds file statistical information.
     *
     * @param information string with file statistical data
     * @see {@link IStatisticService}
     */
    @Override
    public void holdInformation(String information) {

    }
}