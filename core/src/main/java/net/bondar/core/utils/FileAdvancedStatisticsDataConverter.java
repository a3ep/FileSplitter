package net.bondar.core.utils;

import net.bondar.core.domain.FileStatisticsParameter;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
import net.bondar.statistics.service.ParameterObject;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.utils.StatisticsCalculationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Provides converting advanced file statistical data.
 */
public class FileAdvancedStatisticsDataConverter implements IStatisticsDataConverter {

    /**
     * Statistics holder.
     */
    private final IStatisticsHolder holder;

    /**
     * Creates <code>FileAdvancedStatisticsDataConverter</code> instance.
     *
     * @param holder statistics holder
     */
    public FileAdvancedStatisticsDataConverter(IStatisticsHolder holder) {
        this.holder = holder;
    }

    @Override
    public IParameterObject convert() {
        Map<String, IStatObject> records;
        do {
            records = new TreeMap<>(holder.getAllRecords());
        }while (records.isEmpty());
        return new ParameterObject(StatisticsCalculationUtils.calculateCurrentVolume(FileStatisticsParameter.TOTAL_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolume(FileStatisticsParameter.TOTAL_SIZE, records),
                StatisticsCalculationUtils.calculateParametersList(FileStatisticsParameter.PART_WRITTEN, FileStatisticsParameter.PART_SIZE, records));
    }
}
