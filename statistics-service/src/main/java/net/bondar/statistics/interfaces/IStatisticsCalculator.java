package net.bondar.statistics.interfaces;

import net.bondar.statistics.service.CalculatedDataObject;
import net.bondar.statistics.service.ParameterObject;

/**
 * Provides calculating statistical data.
 */
public interface IStatisticsCalculator {

    /**
     * Calculates statistical data.
     *
     * @param parameterObject object with statistics parameters
     * @return <code>ICalculatedDataObject</code>
     */
    CalculatedDataObject calculate(final ParameterObject parameterObject);
}
