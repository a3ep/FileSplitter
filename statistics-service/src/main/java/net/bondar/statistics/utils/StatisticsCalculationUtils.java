package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.interfaces.client.IStatisticsParameter;
import net.bondar.statistics.service.ParameterObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains methods for calculating statistics parameters.
 */
public class StatisticsCalculationUtils {

    /**
     * Calculates volume of performed work.
     *
     * @param parameter  statistics parameter
     * @param recordsMap map with statistics records
     * @return volume of performed work
     */
    public static long calculateCurrentVolume(final IStatisticsParameter parameter,
                                              final Map<String, IStatObject> recordsMap) {
        long currentSize = 0;
        for (Map.Entry<String, IStatObject> entry : recordsMap.entrySet()) {
            currentSize += entry.getValue().getParameterValue(parameter);
        }
        return currentSize;
    }

    /**
     * Calculates total volume of work.
     *
     * @param parameter  statistics parameter
     * @param recordsMap map with statistics records
     * @return total volume of work
     */
    public static double calculateTotalVolume(final IStatisticsParameter parameter,
                                              final Map<String, IStatObject> recordsMap) {
        return (double) recordsMap.entrySet().stream().map(entry ->
                entry.getValue().getParameterValue(parameter)).findFirst().get();
    }

    /**
     * Calculates a list of <code>ParameterObject</code>s.
     *
     * @param current    parameter for calculation current volume
     * @param total      parameter for calculation total volumes
     * @param recordsMap map with statistics records
     * @return a list of <code>ParameterObject</code>s
     */
    public static List<ParameterObject> calculateParametersList(final IStatisticsParameter current,
                                                                final IStatisticsParameter total,
                                                                final Map<String, IStatObject> recordsMap) {
        List<ParameterObject> parameters = new ArrayList<>();
        List<Long> currentVolumes = recordsMap.entrySet().stream().map(entry ->
                entry.getValue().getParameterValue(current)).collect(Collectors.toList());
        List<Double> totalVolumes = recordsMap.entrySet().stream().map(entry ->
                (double) entry.getValue().getParameterValue(total)).collect(Collectors.toList());
        for (int i = 0; i < totalVolumes.size(); i++) {
            parameters.add(new ParameterObject(currentVolumes.get(i), totalVolumes.get(i)));
        }
        return parameters;
    }
}
