package net.bondar;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class StatisticHolder {

    /**
     *
     */
    private Map<String, Long> mapStatistic = new TreeMap<>();

    /**
     *
     * @return
     */
    public Map<String, Long> getMapStatistic() {
        return mapStatistic;
    }

    /**
     * @param threadName
     * @param writeSize
     */
    public synchronized void putInformation(String threadName, long writeSize) {
        if (mapStatistic.containsKey(threadName)) {
            Long oldValue = mapStatistic.get(threadName);
            mapStatistic.put(threadName, oldValue + writeSize);
        } else {
            mapStatistic.put(threadName, writeSize);
        }
    }
}
