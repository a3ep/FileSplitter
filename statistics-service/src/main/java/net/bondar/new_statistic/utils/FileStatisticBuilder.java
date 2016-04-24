package net.bondar.new_statistic.utils;

import net.bondar.new_statistic.exceptions.StatisticException;
import net.bondar.new_statistic.interfaces.IStatisticBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Provides building file statistic information string.
 */
public class FileStatisticBuilder implements IStatisticBuilder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Builds file statistic information string.
     *
     * @param statisticValues list of statistical values
     * @return string with file statistical information
     * @throws StatisticException if one of the statistic parameters is not found
     * @see {@link IStatisticBuilder}
     */
    @Override
    public String buildStatInfoString(List<String> statisticValues) throws StatisticException{
        log.debug("Start building statistic information string.");
        if(statisticValues.isEmpty()){
            return null;
        }
        StringBuilder builder = new StringBuilder("Total progress: ");
        for (String value : statisticValues) {
            builder.append(value + ", ");
        }
        log.debug("Finish building statistic information string. String: " + builder.toString());
        return builder.toString();
    }
}
