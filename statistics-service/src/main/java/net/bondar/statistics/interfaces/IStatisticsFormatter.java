package net.bondar.statistics.interfaces;

/**
 * Provides formatting of statistical data.
 */
public interface IStatisticsFormatter {

    /**
     * Formats statistical data.
     *
     * @param dataObject object with calculated statistical data
     * @return formatted string with statistical information
     */
    String format(final ICalculatedDataObject dataObject);
}
