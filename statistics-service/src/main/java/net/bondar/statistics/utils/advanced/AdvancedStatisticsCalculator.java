package net.bondar.statistics.utils.advanced;

import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsCalculator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides calculation of advanced statistical data.
 */
public class AdvancedStatisticsCalculator implements IStatisticsCalculator {

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
    public List<Double> calculate(final IParameterObject parameterObject) {
        List<Double> result = new ArrayList<>();
        log.debug("Start calculating statistical information.");
        result.add(parameterObject.getCurrentVolume() / parameterObject.getTotalVolume());
        result.addAll(parameterObject.getParameterList().stream().map(object -> object.getCurrentVolume()
                / object.getTotalVolume()).collect(Collectors.toList()));
        result.add(parameterObject.getTotalVolume() / parameterObject.getCurrentVolume());
        log.debug("Finish calculating statistical information: " + result.toString());
        return result;
    }
}
