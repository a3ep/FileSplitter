package net.bondar;

import net.bondar.interfaces.IPart;
import net.bondar.interfaces.IStatisticBuilder;
import net.bondar.interfaces.IStatisticHolder;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class FileStatisticBuilder implements IStatisticBuilder {

    /**
     *
     */
    private final double fileSize;

    /**
     *
     */
    private Map<String, IPart> statInfo;

    /**
     * @param fileSize
     * @param statisticHolder
     */
    public FileStatisticBuilder(double fileSize, IStatisticHolder statisticHolder) {
        this.fileSize = fileSize;
        this.statInfo = statisticHolder.getStatistic();
    }

    public FileStatisticBuilder(List<File> files, IStatisticHolder statisticHolder) {
        fileSize = Calculations.getFileSize(files);
        this.statInfo = statisticHolder.getStatistic();
    }

    /**
     * @return
     */
    public String buildStatisticString() {
        if (statInfo.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder("Total progress : ");
        long totalWrittenSize = 0;
        for (IPart part : statInfo.values()) {
            totalWrittenSize += part.getTotalWrittenSize();
        }
        double totalProgress = totalWrittenSize / fileSize * 100;
        builder.append((int) totalProgress).append(" %, ");
        double threadProgress;
        for (Map.Entry<String, IPart> entry : statInfo.entrySet()) {
            builder.append(entry.getKey()).append(" : ");
            long partSize = entry.getValue().getEndPosition() - entry.getValue().getStartPosition();
            threadProgress = (double) entry.getValue().getWrittenSize() / partSize * 100;
            builder.append((int) threadProgress).append(" %, ");
        }
        double timeRemaining = fileSize / totalWrittenSize;
        builder.append("time remaining : ").append(String.format("%.1f", timeRemaining)).append(" c");
        return builder.toString();
    }
}
