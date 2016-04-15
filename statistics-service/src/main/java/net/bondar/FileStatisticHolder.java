package net.bondar;

import net.bondar.interfaces.IStatisticHolder;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class FileStatisticHolder implements IStatisticHolder{

    /**
     *
     */
    private Map<String, Long> statistic = new TreeMap<>();

    /**
     * @return
     */
    public Map<String, Long> getStatistic() {
        return statistic;
    }

    /**
     *
     * @param threadName
     * @param writeSize
     */
    @Override
    public synchronized void putInformation(String threadName, Long writeSize) {
        if (statistic.containsKey(threadName)) {
            Long oldValue = statistic.get(threadName);
            statistic.put(threadName, oldValue + writeSize);
        } else {
            statistic.put(threadName, writeSize);
        }
    }
}
