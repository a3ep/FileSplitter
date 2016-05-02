package net.bondar.statistics.interfaces;

import java.util.List;

/**
 * Interface for a class that provides formatting of statistical data.
 */
public interface IStatisticsFormatter {

    /**
     * Formats statistical data.
     *
     * @param dataList list with statistical data
     * @return formatted string with statistical information
     */
    String format(List<Double> dataList);
}
