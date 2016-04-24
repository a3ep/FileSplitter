package net.bondar.statistics.interfaces;

import java.util.List;

/**
 * Interface for class that provides building statistic information string.
 */
public interface IStatisticBuilder {

    /**
     * Builds statistic information string.
     *
     * @param statisticValues list of statistical values
     * @return string with statistical information
     */
    String buildStatInfoString(List<String> statisticValues);
}
