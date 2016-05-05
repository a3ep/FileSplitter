package net.bondar.statistics.interfaces;

import java.util.List;

/**
 * Provides formatting of statistical data.
 */
public interface IStatisticsFormatter {

    /**
     * Formats statistical data.
     *
     * @param dataList list with statistical data
     * @return formatted string with statistical information
     */
    String format(final List<Double> dataList);
}
