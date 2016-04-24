package net.bondar.statistics.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Interface for class that provides parsing of statistical information.
 */
public interface IStatisticParser {

    /**
     * Parses statistical data map into map of statistical parameters.
     *
     * @param statisticMap statistical data map
     * @return map of statistical parameters
     */
    Map<String, List<IParameterObject>> parseStatisticalInfo(Map<String, String[]> statisticMap);
}
