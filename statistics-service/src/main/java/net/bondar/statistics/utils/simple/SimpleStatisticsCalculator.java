package net.bondar.statistics.utils.simple;

import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsCalculator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides calculation of simple statistical data.
 */
public class SimpleStatisticsCalculator implements IStatisticsCalculator {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Calculates statistical data.
     *
     * @param parameterObject object with statistics parameters
     * @return list with statistical data
     */
    @Override
    public List<Double> calculate(IParameterObject parameterObject) {
        List<Double> result = new ArrayList<>();
        log.debug("Start calculating statistical information.");
        result.add(parameterObject.getCurrentVolume() / parameterObject.getTotalVolume());
        result.add(parameterObject.getTotalVolume() / parameterObject.getCurrentVolume());
        log.debug("Finish calculating statistical information: " + result.toString());
        return result;
    }
}
