package net.bondar.statistics.interfaces.client;

import net.bondar.statistics.interfaces.IParameterObject;

import java.util.Map;

/**
 * Interface for class that provides converting statistical data.
 */
public interface IStatisticsDataConverter {

    /**
     * Converts statistical data.
     *
     * @param records map with statistics records
     * @return object contains converted statistical data
     */
    IParameterObject convert(Map<String, IStatObject> records);
}
