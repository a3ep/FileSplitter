package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.interfaces.client.IStatisticsParameter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains methods for calculating statistics parameters.
 */
public class StatisticsCalculationUtils {

    /**
     * Calculates volume of performed work
     *
     * @param parameter  statistics parameter
     * @param recordsMap map of statistics records
     * @return volume of performed work
     */
    public static long calculateCurrentVolume(IStatisticsParameter parameter, Map<String, IStatObject> recordsMap) {
        long currentSize = 0;
        for (Map.Entry<String, IStatObject> entry : recordsMap.entrySet()) {
            currentSize += entry.getValue().getParameterByName(parameter);
        }
        return currentSize;
    }

    /**
     * Calculates total volume of work.
     *
     * @param parameter  statistics parameter
     * @param recordsMap map of statistics records
     * @return total volume of work
     */
    public static double calculateTotalVolume(IStatisticsParameter parameter, Map<String, IStatObject> recordsMap) {
//        double totalSize = 0;
//        for (IStatObject object : recordsMap.values()) {
//            totalSize = object.getParameterByName(parameter);
//            break;
//        }
        return (double) recordsMap.entrySet().stream().map(entry -> entry.getValue().getParameterByName(parameter)).findFirst().get();
    }

    /**
     * Calculates a list of volumes of performed works by parts.
     *
     * @param parameter  statistics parameter
     * @param recordsMap map of statistics records
     * @return a list of volumes of performed works by parts
     */
    public static List<Long> calculateCurrentVolumesByParts(IStatisticsParameter parameter, Map<String, IStatObject> recordsMap) {
        return recordsMap.entrySet().stream().map(entry -> entry.getValue().getParameterByName(parameter)).collect(Collectors.toList());
    }

    /**
     * Calculates a list of total volumes by parts.
     *
     * @param parameter  statistics parameter
     * @param recordsMap map of statistics records
     * @return a list of total volumes by parts
     */
    public static List<Double> calculateTotalVolumesByParts(IStatisticsParameter parameter, Map<String, IStatObject> recordsMap) {
        return recordsMap.entrySet().stream().map(entry -> (double) entry.getValue().getParameterByName(parameter)).collect(Collectors.toList());
    }
}
