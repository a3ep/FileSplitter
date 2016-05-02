package net.bondar.statistics.interfaces;

/**
 * Interface for class that provides displaying statistical information.
 */
public interface IStatisticsViewer {

    /**
     * Shows statistical information in log.
     *
     * @param statInfo string with statistical information
     */
    void showInLogs(String statInfo);

    /**
     * Shows statistical information in console.
     *
     * @param statInfo string with statistical information
     */
    void showInConsole(String statInfo);
}
