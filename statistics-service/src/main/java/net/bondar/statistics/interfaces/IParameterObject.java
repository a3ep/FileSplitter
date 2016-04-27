package net.bondar.statistics.interfaces;

import java.util.List;

/**
 * Interface for class that provides holding statistical information parameters.
 */
public interface IParameterObject {

    /**
     * Gets current volume of performed work.
     *
     * @return current volume of performed work
     */
    long getCurrentVolume();

    /**
     * Gets total volume of work.
     *
     * @return total volume of work
     */
    double getTotalVolume();

    /**
     * Gets a list of volumes of performed works by parts.
     *
     * @return a list of volumes of performed works by parts
     */
    List<Long> getCurrentVolumesByParts();

    /**
     * Gets a list of total volumes of works by parts.
     *
     * @return a list of total volumes of works by parts
     */
    List<Double> getTotalVolumesByParts();

}
