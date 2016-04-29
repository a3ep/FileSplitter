package net.bondar.statistics.utils.simple;

import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsCalculator;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
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
     * Statistics converter.
     */
    private final IStatisticsDataConverter converter;

    /**
     * Creates <code>AdvancedStatisticsCalculator</code> instance.
     *
     * @param converter statistics data converter
     */
    public SimpleStatisticsCalculator(IStatisticsDataConverter converter) {
        this.converter = converter;
    }

    @Override
    public List<Double> calculate() {
        List<Double> result = new ArrayList<>();
        IParameterObject parameterObject = converter.convert();
        log.debug("Start calculating statistical information.");
        result.add(parameterObject.getCurrentVolume() / parameterObject.getTotalVolume());
        result.add(parameterObject.getTotalVolume() / parameterObject.getCurrentVolume());
        log.debug("Finish calculating statistical information: " + result.toString());
        return result;
    }
}
