package net.bondar.new_statistic.interfaces;

import java.util.List;

/**
 * Interface for class that provides holding statistical data.
 */
public interface IStatisticHolder {

    /**
     * Gets statistical information.
     *
     * @return list of statistical information
     */
    List<String> getStatisticalInfo();

    /**
     * Holds statistical information.
     *
     * @param information string with statistical data
     */
    void holdStatisticalInfo(String information);

}
