package net.bondar.statistics.interfaces.client;

import net.bondar.statistics.interfaces.IParameterObject;

/**
 * Interface for class that provides converting statistical data.
 */
public interface IStatisticsDataConverter {

    /**
     * Converts statistical data.
     *
     * @return object contains converted statistical data
     */
    IParameterObject convert();
}
