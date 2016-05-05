package net.bondar.statistics.utils.simple;

import net.bondar.statistics.interfaces.IDataObject;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsCalculator;
import net.bondar.statistics.service.DataObject;
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
     * @return object with calculated statistical data
     */
    @Override
    public IDataObject calculate(final IParameterObject parameterObject) {
        log.debug("Start calculating statistical data: " + parameterObject.toString());
        IDataObject result = new DataObject(parameterObject.getListOfIds(),
                parameterObject.getCurrentVolume() / parameterObject.getTotalVolume(),
                parameterObject.getTotalVolume() / parameterObject.getCurrentVolume());
        log.debug("Finish calculating statistical data: " + result.toString());
        return result;
    }
}
