package net.bondar.statistics.interfaces;

import java.util.List;

/**
 * Interface for class that provides calculating statistical data.
 */
public interface IStatisticsCalculator {

    /**
     * Calculates statistical data.
     *
     * @param parameterObject object with statistics parameters
     * @return list of statistical data
     */
    List<Double> calculate(IParameterObject parameterObject);
}
