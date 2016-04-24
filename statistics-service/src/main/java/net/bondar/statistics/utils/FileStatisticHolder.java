package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.IStatisticHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Provides holding file statistical data.
 */
public class FileStatisticHolder implements IStatisticHolder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Map with statistical information.
     */
    private Map<String, String[]> statisticMap = new TreeMap<>();

    /**
     * Gets file statistical information.
     *
     * @return map with file statistical information
     */
    @Override
    public Map<String, String[]> getStatisticalInfo() {
        return statisticMap;
    }

    /**
     * Holds file statistical information.
     *
     * @param information string with file statistical data
     * @see {@link IStatisticHolder}
     */
    @Override
    public synchronized void holdStatisticalInfo(String information) {
        log.debug("Holding statistical information: " + information);
        String key = information.substring(0, information.indexOf(", "));
        String value = information.substring(information.indexOf(", "));
        statisticMap.put(key, value.split(", "));
    }
}
