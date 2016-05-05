package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.IStatisticsViewer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides displaying statistical information.
 */
public class StatisticsViewer implements IStatisticsViewer {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Shows statistical information in log.
     *
     * @param statInfo string with statistical information
     */
    @Override
    public void showInLogs(final String statInfo) {
        log.info(statInfo);
    }

    /**
     * Shows statistical information in console.
     *
     * @param statInfo string with statistical information
     */
    @Override
    public void showInConsole(final String statInfo) {
        System.out.println(statInfo);
    }
}
