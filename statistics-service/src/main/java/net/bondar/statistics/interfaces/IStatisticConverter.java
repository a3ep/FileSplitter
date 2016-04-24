package net.bondar.statistics.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Interface for class that provides converting map with statistical parameters to list of  statistical values.
 */
public interface IStatisticConverter {

    /**
     * Converts map with statistical parameters to list of statistical values.
     *
     * @param statParametersMap map with statistical parameters
     * @return list of statistical values
     */
    List<String> convert(Map<String, List<IParameterObject>> statParametersMap);
}
