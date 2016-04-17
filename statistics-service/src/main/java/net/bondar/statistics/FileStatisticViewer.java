package net.bondar.statistics;

import net.bondar.statistics.interfaces.IStatisticBuilder;
import net.bondar.statistics.interfaces.IStatisticViewer;
import org.apache.log4j.Logger;

/**
 *
 */
public class FileStatisticViewer implements IStatisticViewer {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     *
     */
    private IStatisticBuilder statisticBuilder;

    /**
     * @param statisticBuilder
     */
    public FileStatisticViewer(IStatisticBuilder statisticBuilder) {
        this.statisticBuilder = statisticBuilder;
    }

    /**
     *
     */
    public void showStatistic() {
        String info = statisticBuilder.buildStatisticString();
        if(info!=null) {
            log.info(info);
        }
    }
}
