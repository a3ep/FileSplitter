package net.bondar.statistics.interfaces;

import java.util.List;

/**
 * Provides calculating statistical data.
 */
public interface IStatisticsCalculator {

    /**
     * Calculates statistical data.
     *
     * @param parameterObject object with statistics parameters
     * @return <code>IDataObject</code>
     * @see {@link IDataObject}
     */
    IDataObject calculate(final IParameterObject parameterObject);
}
