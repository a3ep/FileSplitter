package net.bondar.statistics.interfaces;

/**
 * Interface for class that provides displaying statistical information.
 */
public interface IStatisticsViewer {

    /**
     * Shows statistical information in log.
     */
    void showInLogs();

    /**
     * Shows statistical information in console.
     */
    void showInConsole();
}
