package net.bondar.core.utils;

import net.bondar.core.domain.FileStatisticsParameter;
import net.bondar.statistics.domain.ParameterObject;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.ISimpleStatisticsDataConverter;
import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.utils.StatisticsCalculationUtils;

import java.util.Map;

/**
 * Provides converting simple file statistical data.
 */
public class FileSimpleStatisticsDataConverter implements ISimpleStatisticsDataConverter {
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

    @Override
    public IParameterObject convert() {
        Map<String, IStatObject> records = holder.getAllRecords();
        return new ParameterObject(StatisticsCalculationUtils.calculateCurrentVolume(FileStatisticsParameter.TOTAL_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolume(FileStatisticsParameter.TOTAL_SIZE, records));
    }
}
