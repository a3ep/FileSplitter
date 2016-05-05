package net.bondar.statistics.interfaces.client;

import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsHolder;

import java.util.Map;

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
    IParameterObject convert(IStatisticsHolder holder);
}
