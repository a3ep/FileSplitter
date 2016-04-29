package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.IStatisticsFormatter;
import net.bondar.statistics.interfaces.IStatisticsViewer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides displaying file statistical information.
 */
public class StatisticsViewer implements IStatisticsViewer {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Statistics formatter.
     */
    private final IStatisticsFormatter formatter;

    /**
     * Creates <code>StatisticsViewer</code> instance.
     *
     * @param formatter statistics formatter
     */
    public StatisticsViewer(IStatisticsFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void showInLogs() {
        log.info(formatter.format());
    }

    @Override
    public void showInConsole() {
       System.out.println(formatter.format());
    }
}
