package net.bondar.statistics;

import net.bondar.statistics.interfaces.IPartObject;
import net.bondar.statistics.interfaces.IStatisticHolder;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class FileStatisticHolder implements IStatisticHolder {

    /**
     *
     */
    private Map<String, IPartObject> statistic = new TreeMap<>();

    /**
     * @return
     */
    public Map<String, IPartObject> getStatistic() {
        return statistic;
    }

    /**
     * @param threadName
     * @param filePart
     */
    @Override
    public synchronized void putInformation(String threadName, IPartObject filePart) {
        statistic.put(threadName, filePart);
    }
}

