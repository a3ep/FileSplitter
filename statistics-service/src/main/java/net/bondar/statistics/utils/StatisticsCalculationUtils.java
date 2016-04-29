package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.IParameterObject;
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
        return (double) recordsMap.entrySet().stream().map(entry -> entry.getValue().getParameterByName(parameter)).findFirst().get();
    }

    /**
     * Calculates a list of <code>ParameterObject</code>s.
     *
     * @param current parameter for calculation current volume
     * @param total parameter for calculation total volumes
     * @param recordsMap map of statistics records
     * @return a list of <code>ParameterObject</code>s
     */
    public static List<ParameterObject> calculateParametersList(IStatisticsParameter current, IStatisticsParameter total, Map<String, IStatObject> recordsMap){
        List<ParameterObject> parameters = new ArrayList<>();
        List<Long> currentVolumes = recordsMap.entrySet().stream().map(entry -> entry.getValue().getParameterByName(current)).collect(Collectors.toList());
        List<Double> totalVolumes = recordsMap.entrySet().stream().map(entry -> (double) entry.getValue().getParameterByName(total)).collect(Collectors.toList());
        for(int i=0; i<totalVolumes.size();i++){
            parameters.add(new ParameterObject(currentVolumes.get(i), totalVolumes.get(i)));
        }
        return parameters;
    }
}
