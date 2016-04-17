package net.bondar;

import net.bondar.interfaces.IPart;
import net.bondar.interfaces.IStatisticHolder;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class FileStatisticHolder implements IStatisticHolder {

    /**
     *
     */
    private Map<String, IPart> statistic = new TreeMap<>();

    /**
     * @return
     */
    public Map<String, IPart> getStatistic() {
        return statistic;
    }

    /**
     * @param threadName
     * @param filePart
     */
    @Override
    public synchronized void putInformation(String threadName, IPart filePart) {
        statistic.put(threadName, filePart);
    }
}

