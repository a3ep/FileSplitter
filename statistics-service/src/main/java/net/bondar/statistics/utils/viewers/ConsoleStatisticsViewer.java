package net.bondar.statistics.utils.viewers;

import net.bondar.statistics.interfaces.IStatisticsViewer;

/**
 * Provides displaying statistical information to the console.
 */
public class ConsoleStatisticsViewer implements IStatisticsViewer {

    /**
     * Shows statistical information in console.
     *
     * @param statInfo string with statistical information
     */
    @Override
    public void show(final String statInfo) {
        System.out.println(statInfo);
    }
}
