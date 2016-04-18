package net.bondar.statistics;

import net.bondar.calculations.Calculations;
import net.bondar.statistics.interfaces.IPartObject;
import net.bondar.statistics.interfaces.IStatisticBuilder;
import net.bondar.statistics.interfaces.IStatisticHolder;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Provides building statistic information about file.
 */
class FileStatisticBuilder implements IStatisticBuilder {

    /**
     * Size of the specified file.
     */
    private final double fileSize;

    /**
     * Map contains information from threads.
     */
    private Map<String, IPartObject> infoMap;

    /**
     * Creates <code>FileStatisticBuilder</code> instance.
     *
     * @param fileSize   size of the specified file
     * @param statHolder statistic holder
     */
    FileStatisticBuilder(double fileSize, IStatisticHolder statHolder) {
        this.fileSize = fileSize;
        this.infoMap = statHolder.getStatistic();
    }

    /**
     * Creates <code>FileStatisticBuilder</code> instance.
     *
     * @param files      list of part-files
     * @param statHolder statistic holder
     * @see {@link IStatisticBuilder}
     */
    FileStatisticBuilder(List<File> files, IStatisticHolder statHolder) {
        fileSize = Calculations.getFileSize(files);
        this.infoMap = statHolder.getStatistic();
    }

    /**
     * Builds statistic information about file.
     *
     * @return string with statistic information about file
     * @see {@link IStatisticBuilder}
     */
    public String buildStatisticString() {
        if (infoMap.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder("Total progress : ");
        long totalWrittenSize = 0;
        for (IPartObject part : infoMap.values()) {
            totalWrittenSize += part.getTotalWrittenSize();
        }
        double totalProgress = totalWrittenSize / fileSize * 100;
        builder.append((int) totalProgress).append(" %, ");
        double threadProgress;
        for (Map.Entry<String, IPartObject> entry : infoMap.entrySet()) {
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
