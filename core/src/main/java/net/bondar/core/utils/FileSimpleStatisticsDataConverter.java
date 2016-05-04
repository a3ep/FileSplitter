package net.bondar.core.utils;

import net.bondar.core.FileStatisticsParameter;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
import net.bondar.statistics.service.ParameterObject;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.utils.StatisticsCalculationUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Provides converting simple file statistical data.
 */
public class FileSimpleStatisticsDataConverter implements IStatisticsDataConverter {

    /**
     * Statistics holder.
     */
    private final IStatisticsHolder holder;

    /**
     * Creates <code>FileAdvancedStatisticsDataConverter</code> instance.
     *
     * @param holder statistics holder
     */
    public FileSimpleStatisticsDataConverter(IStatisticsHolder holder) {
        this.holder = holder;
    }

    /**
     * Converts file statistical data.
     *
     * @param records map with statistics records
     * @return object contains converted statistical data
     */
    @Override
    public IParameterObject convert(Map<String, IStatObject> records) {
        while(records.isEmpty()){
            records = new TreeMap<>(holder.getAllRecords());
        }
        return new ParameterObject(StatisticsCalculationUtils.calculateCurrentVolume(FileStatisticsParameter.TOTAL_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolume(FileStatisticsParameter.TOTAL_SIZE, records));
    }
}
