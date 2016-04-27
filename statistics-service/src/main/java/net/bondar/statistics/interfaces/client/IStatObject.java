package net.bondar.statistics.interfaces.client;

import java.util.Map;

/**
 * Interface for class that contains parameters for calculating statistical information.
 */
public interface IStatObject {

    /**
     * Gets all parameters.
     *
     * @return map contains a names of parameters and a parameters values
     */
    Map<IStatisticsParameter, Long> getAllParameters();

    /**
     * Gets parameter value for name.
     *
     * @param name statistics parameter
     * @return parameter value
     */
    long getParameterByName(IStatisticsParameter name);


}
