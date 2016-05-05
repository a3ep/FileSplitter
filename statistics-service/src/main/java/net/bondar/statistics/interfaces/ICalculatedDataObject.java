package net.bondar.statistics.interfaces;

import java.util.List;

/**
 * Provides holding calculated statistical data.
 */
public interface ICalculatedDataObject {

    /**
     * Gets total progress.
     *
     * @return total progress value
     */
    double getTotalProgress();

    /**
     * Gets list of parts progress.
     *
     * @return list of parts progress value
     */
    List<Double> getPartsProgress();

    /**
     * Gets time remaining.
     *
     * @return time remaining value
     */
    double getTimeRemaining();

    /**
     * Gets list of statitics records ids.
     *
     * @return list of statistics records ids
     */
    List<String> getListOfIds();
}
