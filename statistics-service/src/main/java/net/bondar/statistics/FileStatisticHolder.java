package net.bondar.statistics;

import net.bondar.statistics.interfaces.IPartObject;
import net.bondar.statistics.interfaces.IStatisticHolder;

import java.util.Map;
import java.util.TreeMap;

/**
 * Provides holding statistic information about file.
 */
public class FileStatisticHolder implements IStatisticHolder {

    /**
     * Map contains information about file from threads.
     */
    private Map<String, IPartObject> statistic = new TreeMap<>();

    /**
     * Gets map with information about file from threads.
     *
     * @return map with information about file
     */
    public Map<String, IPartObject> getStatistic() {
        return statistic;
    }

    /**
     * Puts information about file from threads into information map.
     *
     * @param threadName name of the thread
     * @param filePart   <code>IPartObject</code> with parameters for calculating statistic
     */
    @Override
    public synchronized void putInformation(String threadName, IPartObject filePart) {
        statistic.put(threadName, filePart);
    }
}

