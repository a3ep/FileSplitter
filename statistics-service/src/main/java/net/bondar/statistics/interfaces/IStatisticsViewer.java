package net.bondar.statistics.interfaces;

/**
 * Provides displaying statistical information.
 */
public interface IStatisticsViewer {

    /**
     * Shows statistical information in log.
     *
     * @param statInfo string with statistical information
     */
    void showInLogs(final String statInfo);

    /**
     * Shows statistical information in console.
     *
     * @param statInfo string with statistical information
     */
    void showInConsole(final String statInfo);
}
