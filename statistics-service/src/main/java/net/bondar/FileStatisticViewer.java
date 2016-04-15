package net.bondar;

import net.bondar.interfaces.IStatisticBuilder;
import net.bondar.interfaces.IStatisticViewer;
import org.apache.log4j.Logger;

/**
 *
 */
public class FileStatisticViewer implements IStatisticViewer {

    /**
     *
     */
    private final Logger log = Logger.getLogger("userLogger");

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
        if(info!=null) log.info(info);
    }
}
