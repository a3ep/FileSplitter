package net.bondar.statistics.utils.simple;

import net.bondar.statistics.interfaces.IStatisticsCalculator;
import net.bondar.statistics.service.CalculatedDataObject;
import net.bondar.statistics.service.ParameterObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
     * @return object with calculated statistical data
     */
    @Override
    public CalculatedDataObject calculate(final ParameterObject parameterObject) {
        log.debug("Start calculating statistical data: " + parameterObject.toString());
        CalculatedDataObject result = new CalculatedDataObject(parameterObject.getListOfIds(),
                parameterObject.getCurrentVolume() / parameterObject.getTotalVolume(),
                parameterObject.getTotalVolume() / parameterObject.getCurrentVolume());
        log.debug("Finish calculating statistical data: " + result.toString());
        return result;
    }
}
