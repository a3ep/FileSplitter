package net.bondar.core.utils;

import net.bondar.core.domain.FileStatisticsParameter;
import net.bondar.statistics.domain.ParameterObject;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.IAdvancedStatisticsDataConverter;
import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.utils.StatisticsCalculationUtils;

import java.util.Map;

/**
 * Provides converting advanced file statistical data.
 */
public class FileAdvancedStatisticsDataConverter implements IAdvancedStatisticsDataConverter {

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
        Map<String, IStatObject> records = holder.getAllRecords();
        return new ParameterObject(StatisticsCalculationUtils.calculateCurrentVolume(FileStatisticsParameter.TOTAL_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolume(FileStatisticsParameter.TOTAL_SIZE, records),
                StatisticsCalculationUtils.calculateCurrentVolumesByParts(FileStatisticsParameter.PART_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolumesByParts(FileStatisticsParameter.PART_SIZE, records));
    }
}
