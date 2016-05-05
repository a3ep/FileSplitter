package net.bondar.core.utils.converters;

import net.bondar.core.FileStatisticsParameter;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
import net.bondar.statistics.service.ParameterObject;
import net.bondar.statistics.utils.StatisticsCalculationUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Provides converting simple file statistical data.
 */
public class SimpleStatisticsDataConverter implements IStatisticsDataConverter {

    /**
     * Converts file statistical data.
     *
     * @param holder statistics holder
     * @return object contains converted statistical data
     */
    @Override
    public IParameterObject convert(IStatisticsHolder holder) {
        Map<String, IStatObject> records = new TreeMap<>();
        while (records.isEmpty()) {
            records = holder.getAllRecords();
        }
        return new ParameterObject(StatisticsCalculationUtils.calculateCurrentVolume(FileStatisticsParameter.TOTAL_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolume(FileStatisticsParameter.TOTAL_SIZE, records));
    }
}
