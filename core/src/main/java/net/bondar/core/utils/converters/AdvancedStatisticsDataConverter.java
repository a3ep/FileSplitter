package net.bondar.core.utils.converters;

import net.bondar.core.FileStatisticsParameter;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
import net.bondar.statistics.service.ParameterObject;
import net.bondar.statistics.utils.StatisticsCalculationUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Provides converting advanced file statistical data.
 */
public class AdvancedStatisticsDataConverter implements IStatisticsDataConverter {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Converts file statistical data.
     *
     * @param holder statistics holder
     * @return object contains converted file statistical data
     */
    @Override
    public IParameterObject convert(IStatisticsHolder holder) {
        Map<String, IStatObject> records = new TreeMap<>();
        while (records.isEmpty()) {
            records = holder.getAllRecords();
        }
        log.debug("Converting statistical data: " + records.values());
        return new ParameterObject(holder.getAllRecordsIds(), StatisticsCalculationUtils.calculateCurrentVolume(FileStatisticsParameter.TOTAL_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolume(FileStatisticsParameter.TOTAL_SIZE, records),
                StatisticsCalculationUtils.calculateParametersList(FileStatisticsParameter.PART_WRITTEN, FileStatisticsParameter.PART_SIZE, records));
    }
}
