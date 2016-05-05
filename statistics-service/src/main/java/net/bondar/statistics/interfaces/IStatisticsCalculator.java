package net.bondar.statistics.interfaces;

/**
 * Provides calculating statistical data.
 */
public interface IStatisticsCalculator {

    /**
     * Calculates statistical data.
     *
     * @param parameterObject object with statistics parameters
     * @return <code>ICalculatedDataObject</code>
     * @see {@link ICalculatedDataObject}
     */
    ICalculatedDataObject calculate(final IParameterObject parameterObject);
}
