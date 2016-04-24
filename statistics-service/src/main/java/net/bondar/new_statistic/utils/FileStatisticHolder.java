package net.bondar.new_statistic.utils;

import net.bondar.new_statistic.interfaces.IStatisticHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides holding file statistical data.
 */
public class FileStatisticHolder implements IStatisticHolder{

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * List with statistical information.
     */
    private List<String> statisticList = new ArrayList<>();

    /**
     * Gets file statistical information.
     *
     * @return list with file statistical information
     */
    @Override
    public List<String> getStatisticalInfo() {
        return statisticList;
    }

    /**
     * Holds file statistical information.
     *
     * @param information string with file statistical data
     * @see {@link IStatisticHolder}
     */
    @Override
    public synchronized void holdStatisticalInfo(String information) {
        log.debug("Holding statistical information: "+information);
        statisticList.add(information);
    }
}
