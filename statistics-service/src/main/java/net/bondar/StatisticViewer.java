package net.bondar;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;

/**
 *
 */
public class StatisticViewer {

    /**
     *
     */
    private final Logger log = Logger.getLogger("userLogger");

    /**
     *
     */
    private final double fileSize;

    /**
     *
     */
    private StatisticHolder statisticHolder;

    /**
     *
     */
    private Map<String, Long> statInfo;

    /**
     *
     * @param fileDest
     * @param statisticHolder
     */
    public StatisticViewer(String fileDest, StatisticHolder statisticHolder) {
        fileSize = new File(fileDest).length();
        this.statisticHolder = statisticHolder;
        this.statInfo = statisticHolder.getMapStatistic();
    }

    /**
     *
     */
    public void showStatistic() {
        if(statInfo.isEmpty())return;
        StringBuilder builder = new StringBuilder("Total progress : ");
        long totalWriteSize = 1;
        for (Long value : statInfo.values()) {
            totalWriteSize += value;
        }
        double totalProgress = totalWriteSize / fileSize * 100;
        builder.append((int)totalProgress).append(" %, ");
        double threadProgress;
        for (Map.Entry<String, Long> entry : statInfo.entrySet()) {
            builder.append(entry.getKey()).append(" : ");
            threadProgress = (double)entry.getValue() / fileSize * 100;
            builder.append((int)threadProgress).append(" %, ");
        }
        double timeRemaining = fileSize / totalWriteSize;
        builder.append("time remaining : ").append((int)timeRemaining).append(" c");
        log.info(builder.toString());
    }
}
