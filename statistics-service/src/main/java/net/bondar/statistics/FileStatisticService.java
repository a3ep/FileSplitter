package net.bondar.statistics;

import net.bondar.statistics.interfaces.*;

import java.util.Timer;

/**
 * Provides file statistical information management.
 */
class FileStatisticService implements IStatisticService {

    /**
     * Statistic holder.
     */
    private IStatisticHolder holder;

    /**
     * Statistic builder.
     */
    private IStatisticBuilder builder;

    /**
     * Statistic timer task.
     */
    private AbstractStatisticTimerTask timerTask;

    /**
     * Timer.
     */
    private Timer timer = new Timer();

    /**
     * Creates <code>FileStatisticService</code> instance.
     *
     * @param holder    statistic holder
     * @param builder   statistic builder
     * @param timerTask statistic timer task
     * @see {@link IStatisticService}
     */
    FileStatisticService(IStatisticHolder holder, IStatisticBuilder builder, AbstractStatisticTimerTask timerTask) {
        this.holder = holder;
        this.builder = builder;
        this.timerTask = timerTask;
    }

    /**
     * Holds information about file from threads into statistic information map.
     *
     * @param threadName name of the thread
     * @param filePart   <code>IPartObject</code> with parameters for calculating statistic
     * @see {@link IStatisticService}
     */
    public void holdInformation(String threadName, IPartObject filePart) {
        holder.putInformation(threadName, filePart);
    }

    /**
     * Builds string with statistical information about file processing.
     *
     * @return string with statistical information
     * @see {@link IStatisticService}
     */
    public String buildStatisticString() {
        return builder.buildStatisticString();
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
    public void show(int delay, int period) {
        timer.schedule(timerTask, delay, period);
    }

    /**
     * Ends showing statistical information about file processing to user.
     *
     * @see {@link IStatisticService}
     */
    public void hide() {
        timer.cancel();
    }
}
