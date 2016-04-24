package net.bondar.new_statistic.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Interface for class that provides parsing of statistical information.
 */
public interface IStatisticParser {

    /**
     * Parses statistical information list into map of statistical parameters.
     *
     * @param statisticList statistical information list
     * @return map of statistical parameters
     */
    Map<String, List<IParameterObject>> parseStatisticalInfo(List<String> statisticList);
}
