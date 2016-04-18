package net.bondar.statistics.interfaces;

import java.util.Map;

/**
 * Interface for class that provides holding statistical information.
 */
public interface IStatisticHolder {

    /**
     * Gets map with information from threads.
     *
     * @return map with information
     */
    Map<String, IPartObject> getStatistic();

    /**
     * Puts information from threads into information map.
     *
     * @param threadName name of the thread
     * @param filePart   <code>IPartObject</code> with parameters for calculating statistic
     */
    void putInformation(String threadName, IPartObject filePart);
}
