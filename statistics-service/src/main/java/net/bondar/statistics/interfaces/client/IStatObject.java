package net.bondar.statistics.interfaces.client;

import java.util.Map;

/**
 * Contains parameters for calculating statistical information.
 */
public interface IStatObject {

    /**
     * Gets all parameters.
     *
     * @return map contains a names of parameters and a parameters values
     */
    Map<IStatisticsParameter, Long> getAllParameters();

    /**
     * Gets parameter value.
     *
     * @param parameter statistics parameter
     * @return parameter value
     */
    long getParameterValue(final IStatisticsParameter parameter);
}
