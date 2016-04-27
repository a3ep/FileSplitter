package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsCalculator;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides calculation of statistical data.
 */
public class StatisticsCalculator implements IStatisticsCalculator {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Statistics converter.
     */
    private final IStatisticsDataConverter converter;

    /**
     * Creates <code>StatisticsCalculator</code> instance.
     *
     * @param converter statistics data converter
     */
    public StatisticsCalculator(IStatisticsDataConverter converter) {
        this.converter = converter;
    }

    @Override
    public List<Double> calculate() {
        List<Double> result = new ArrayList<>();
        IParameterObject parameterObject = converter.convert();
        log.debug("Start calculating statistical information.");
        result.add(parameterObject.getCurrentVolume() / parameterObject.getTotalVolume());
        for (int i = 0; i < parameterObject.getTotalVolumesByParts().size(); i++) {
            result.add(parameterObject.getCurrentVolumesByParts().get(i) / parameterObject.getTotalVolumesByParts().get(i));
        }
        result.add(parameterObject.getTotalVolume() / parameterObject.getCurrentVolume());
        log.debug("Finish calculating statistical information: " + result.toString());
        return result;
    }
}
