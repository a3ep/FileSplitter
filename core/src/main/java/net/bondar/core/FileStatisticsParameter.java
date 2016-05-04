package net.bondar.core;

import net.bondar.statistics.interfaces.client.IStatisticsParameter;

/**
 * Contains file statistics parameters.
 */
public enum FileStatisticsParameter implements IStatisticsParameter {
    PART_WRITTEN,
    PART_SIZE,
    TOTAL_WRITTEN,
    TOTAL_SIZE;
}
