package net.bondar.statistics.interfaces;

import java.util.Map;

/**
 * Interface for class that provides holding statistical data.
 */
public interface IStatisticHolder {

    /**
     * Gets statistical information.
     *
     * @return map with statistical information
     */
    Map<String, String[]> getStatisticalInfo();

    /**
     * Holds statistical information.
     *
     * @param information string with statistical data
     */
    void holdStatisticalInfo(String information);

}
