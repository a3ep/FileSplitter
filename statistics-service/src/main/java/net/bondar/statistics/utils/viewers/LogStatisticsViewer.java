package net.bondar.statistics.utils.viewers;

import net.bondar.statistics.interfaces.IStatisticsViewer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides displaying statistical information in the logs.
 */
public class LogStatisticsViewer implements IStatisticsViewer {

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
    public void show(final String statInfo) {
        log.info(statInfo);
    }


}
