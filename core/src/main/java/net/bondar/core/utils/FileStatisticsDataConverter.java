package net.bondar.core.utils;

import net.bondar.core.domain.FileStatisticsParameter;
import net.bondar.statistics.domain.ParameterObject;
import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IParameterObject;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
import net.bondar.statistics.utils.StatisticsCalculationUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Provides converting file statistical data.
 */
public class FileStatisticsDataConverter implements IStatisticsDataConverter {

    /**
     * Statistics holder.
     */
    private final IStatisticsHolder holder;

    /**
     * Creates <code>FileStatisticsDataConverter</code> instance.
     *
     * @param holder statistics holder
     */
    public FileStatisticsDataConverter(IStatisticsHolder holder) {
        this.holder = holder;
    }

    @Override
    public IParameterObject convert() throws StatisticsException {
        Map<String, IStatObject> records = holder.getAllRecords();
        return new ParameterObject(StatisticsCalculationUtils.calculateCurrentVolume(FileStatisticsParameter.TOTAL_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolume(FileStatisticsParameter.TOTAL_SIZE, records),
                StatisticsCalculationUtils.calculateCurrentVolumesByParts(FileStatisticsParameter.PART_WRITTEN, records),
                StatisticsCalculationUtils.calculateTotalVolumesByParts(FileStatisticsParameter.PART_SIZE, records));
    }
}
