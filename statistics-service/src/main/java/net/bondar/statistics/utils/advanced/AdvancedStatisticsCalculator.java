package net.bondar.statistics.utils.advanced;

import net.bondar.statistics.interfaces.ICalculatedDataObject;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsCalculator;
import net.bondar.statistics.service.CalculatedDataObject;
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
     * @return object with calculated statistical data
     */
    @Override
    public ICalculatedDataObject calculate(final IParameterObject parameterObject) {
        log.info("Start calculating statistical data: " + parameterObject.toString());
        List<Double> partsProgress = new ArrayList<>();
        partsProgress.addAll(parameterObject.getParameterList().stream().map(object -> object.getCurrentVolume()
                / object.getTotalVolume()).collect(Collectors.toList()));
        ICalculatedDataObject result = new CalculatedDataObject(parameterObject.getListOfIds(),
                parameterObject.getCurrentVolume() / parameterObject.getTotalVolume(), partsProgress,
                parameterObject.getTotalVolume() / parameterObject.getCurrentVolume());
        log.debug("Finish calculating statistical data: " + result.toString());
        return result;
    }
}
