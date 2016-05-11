package net.bondar.statistics.interfaces.client;

import net.bondar.statistics.service.ParameterObject;
import net.bondar.statistics.utils.StatisticsHolder;

/**
 * Provides converting statistical data.
 */
public interface IStatisticsDataConverter {

    /**
     * Converts statistical data.
     *
     * @param holder statistics holder
     * @return object contains converted statistical data
     */
    ParameterObject convert(StatisticsHolder holder);
}
