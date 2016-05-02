package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.IStatisticsViewer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides displaying file statistical information.
 */
public class StatisticsViewer implements IStatisticsViewer {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    @Override
    public void showInLogs(String statInfo) {
        log.info(statInfo);
    }

    @Override
    public void showInConsole(String statInfo) {
        System.out.println(statInfo);
    }
}
