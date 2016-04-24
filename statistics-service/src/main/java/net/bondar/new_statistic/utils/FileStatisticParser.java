package net.bondar.new_statistic.utils;

import net.bondar.new_statistic.domain.ParameterObject;
import net.bondar.new_statistic.interfaces.IParameterObject;
import net.bondar.new_statistic.interfaces.IStatisticParser;
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
     * Parses file statistical information list to map with file statistical parameters.
     *
     * @param statisticList statistical information list
     * @return map with file statistical parameters
     * @see {@link IStatisticParser}
     */
    @Override
    public Map<String, List<IParameterObject>> parseStatisticalInfo(List<String> statisticList) {
        log.debug("Start parsing statistical information list.");
        Map<String, List<IParameterObject>> statParametersMap = new TreeMap<>();
        if(statisticList.isEmpty()){
            return statParametersMap;
        }
        for (String str : statisticList) {
            String[] array = str.split(", ");
            List<IParameterObject> parameterList = new ArrayList<>();
            IParameterObject start = new ParameterObject("start", true, array[1]);
            IParameterObject end = new ParameterObject("end", true, array[2]);
            IParameterObject writtenSize = new ParameterObject("writtenSize", true, array[3]);
            IParameterObject totalWrittenSize = new ParameterObject("totalWrittenSize", true, array[4]);
            IParameterObject fileSize = new ParameterObject("fileSize", true, array[5]);
            parameterList.add(start);
            parameterList.add(end);
            parameterList.add(writtenSize);
            parameterList.add(totalWrittenSize);
            parameterList.add(fileSize);
            statParametersMap.put(array[0], parameterList);
        }
        log.debug("Finish parsing statistical information list.");
        return statParametersMap;
    }
}