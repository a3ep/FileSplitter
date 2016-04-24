package net.bondar.statistics.utils;

import net.bondar.statistics.domain.ParameterObject;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Provides parsing of file statistical information.
 */
public class FileStatisticParser implements IStatisticParser {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parses file statistical data map into map with file statistical parameters.
     *
     * @param statisticMap statistical data map
     * @return map with file statistical parameters
     * @see {@link IStatisticParser}
     */
    @Override
    public Map<String, List<IParameterObject>> parseStatisticalInfo(Map<String, String[]> statisticMap) {
        log.debug("Start parsing statistical information list.");
        Map<String, String[]> map = new TreeMap<>(statisticMap);
        Map<String, List<IParameterObject>> statParametersMap = new TreeMap<>();
        if (statisticMap.isEmpty()) {
            return statParametersMap;
        }
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            List<IParameterObject> parameterList = new ArrayList<>();
            IParameterObject start = new ParameterObject("start", entry.getValue()[1]);
            IParameterObject end = new ParameterObject("end", entry.getValue()[2]);
            IParameterObject writtenSize = new ParameterObject("writtenSize", entry.getValue()[3]);
            IParameterObject totalWrittenSize = new ParameterObject("totalWrittenSize", entry.getValue()[4]);
            IParameterObject fileSize = new ParameterObject("fileSize", entry.getValue()[5]);
            parameterList.add(start);
            parameterList.add(end);
            parameterList.add(writtenSize);
            parameterList.add(totalWrittenSize);
            parameterList.add(fileSize);
            statParametersMap.put(entry.getKey(), parameterList);
        }
        log.debug("Finish parsing statistical information list.");
        return statParametersMap;
    }
}
