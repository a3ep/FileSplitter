package net.bondar.statistics.utils;

import net.bondar.statistics.exceptions.StatisticException;
import net.bondar.statistics.interfaces.IStatisticViewer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides displaying file statistical information.
 */
public class FileStatisticViewer implements IStatisticViewer {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Shows statistical information about file processing in logs.
     *
     * @param statistic string with file statistical information
     * @throws StatisticException if one of the statistic parameters is not found
     * @see {@link IStatisticViewer}
     */
    public void showStatistic(String statistic) throws StatisticException {
        if (statistic != null) {
            log.info(statistic);
        }
    }
}
