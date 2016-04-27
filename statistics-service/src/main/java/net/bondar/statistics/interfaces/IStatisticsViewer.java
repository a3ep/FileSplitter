package net.bondar.statistics.interfaces;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface for class that provides displaying statistical information.
 */
public interface IStatisticsViewer {

    /**
     * Shows statistical information in log.
     *
     * @param disable flag for cancellation information display
     */
    void showInLogs(AtomicBoolean disable);

    /**
     * Shows statistical information in console.
     *
     * @param disable flag for cancellation information display
     */
    void showInConsole(AtomicBoolean disable);
}
