package net.bondar;

import net.bondar.interfaces.IStatisticBuilder;
import net.bondar.interfaces.IStatisticHolder;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class FileStatisticBuilder implements IStatisticBuilder{

    /**
     *
     */
    private final double fileSize;

    /**
     *
     */
    private Map<String, Long> statInfo;

    /**
     *
     * @param fileSize
     * @param statisticHolder
     */
    public FileStatisticBuilder(double fileSize, IStatisticHolder statisticHolder) {
        this.fileSize = fileSize;
        this.statInfo = statisticHolder.getStatistic();
    }

    public FileStatisticBuilder(List<File> files, IStatisticHolder statisticHolder) {
        fileSize = getFileSize(files);
        this.statInfo = statisticHolder.getStatistic();
    }

    /**
     *
     * @return
     */
    public String buildStatisticString() {
        if(statInfo.isEmpty())return null;
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
        return builder.toString();
    }

    private double getFileSize(List<File> files){
        double fileSize=0;
        for (File f: files){
            fileSize+=f.length();
        }
        return fileSize;
    }

}
