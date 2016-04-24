package net.bondar.statistics.utils;

import net.bondar.statistics.exceptions.StatisticException;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides converting map with file statistical parameters to list of file statistical values.
 */
public class FileStatisticConverter implements IStatisticConverter {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Converts map with file statistical parameters to list of statistical string parts.
     *
     * @param statParametersMap map with statistical parameters
     * @return list of file statistical values
     * @throws StatisticException if the parameter is not found
     * @see {@link IStatisticConverter}
     */
    @Override
    public List<String> convert(Map<String, List<IParameterObject>> statParametersMap) throws StatisticException {
        log.debug("Start converting map with statistic parameters.");
        List<String> statisticValues = new ArrayList<>();
        if (statParametersMap.isEmpty()) {
            return statisticValues;
        }
        double totalWrittenSize = 0;
        long fileSize = 0;
        for (Map.Entry<String, List<IParameterObject>> entry : statParametersMap.entrySet()) {
            for (IParameterObject parameter : entry.getValue()) {
                if (parameter.getName().equals("totalWrittenSize")) {
                    totalWrittenSize += Long.parseLong(parameter.getValue());
                }
            }
            if (fileSize == 0) {
                fileSize = Long.parseLong(getParameterValue("fileSize", entry.getValue()));
            }
        }
        statisticValues.add((int) (totalWrittenSize / fileSize * 100) + "%");
        for (Map.Entry<String, List<IParameterObject>> entry : statParametersMap.entrySet()) {
            long partSize = Long.parseLong(getParameterValue("end", entry.getValue()))
                    - Long.parseLong(getParameterValue("start", entry.getValue()));
            double writtenSize = Double.parseDouble(getParameterValue("writtenSize", entry.getValue()));
            statisticValues.add(entry.getKey() + " : " + (int) (writtenSize / partSize * 100) + "%");
        }
        double timeRemaining = fileSize / totalWrittenSize;
        statisticValues.add("time remaining : " + String.format("%.1f", timeRemaining) + " c");
        log.debug("Finish converting. Statistic values: " + statisticValues.toString());
        return statisticValues;
    }

    /**
     * Gets parameter value.
     *
     * @param parameterName specified parameter name
     * @param parameterList parameter list
     * @return parameter value
     * @throws StatisticException if the parameter is not found
     */
    private String getParameterValue(String parameterName, List<IParameterObject> parameterList) throws StatisticException {
        for (IParameterObject parameter : parameterList) {
            if (parameter.getName().equals(parameterName)) {
                return parameter.getValue();
            }
        }
        log.error("Error while finding parameter value for parameter: " + parameterName);
        throw new StatisticException("Error while finding parameter value for parameter: " + parameterName);
    }
}
