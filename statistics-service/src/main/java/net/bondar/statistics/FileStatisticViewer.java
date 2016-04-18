package net.bondar.statistics;

import net.bondar.statistics.interfaces.IStatisticBuilder;
import net.bondar.statistics.interfaces.IStatisticViewer;
import org.apache.log4j.Logger;

/**
 * Provides showing statistical information about file processing.
 */
public class FileStatisticViewer implements IStatisticViewer {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Statistic builder.
     */
    private IStatisticBuilder builder;

    /**
     * Creates <code>FileStatisticViewer</code> instance.
     *
     * @param builder statistic builder
     */
    public FileStatisticViewer(IStatisticBuilder builder) {
        this.builder = builder;
    }

    /**
     * Shows statistical information about file processing in logs.
     */
    public void showStatistic() {
        String info = builder.buildStatisticString();
        if (info != null) {
            log.info(info);
        }
    }
}
