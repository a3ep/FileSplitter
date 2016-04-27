package net.bondar.statistics.domain;

import net.bondar.statistics.interfaces.IParameterObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides holding statistical parameters.
 */
public class ParameterObject implements IParameterObject {

    /**
     * Current volume of performed work.
     */
    private final long currentVolume;

    /**
     * Total volume of work.
     */
    private final double totalVolume;

    /**
     * List of volumes of performed works by parts
     */
    private final List<Long> currentVolumesByParts;

    /**
     * List of total volumes of works by parts.
     */
    private final List<Double> totalVolumesByParts;

    /**
     * Creates <code>ParameterObject</code> instance.
     *
     * @param currentVolume         current volume of performed work
     * @param totalVolume           total volume of work
     * @param currentVolumesByParts a list of volumes of performed works by parts
     * @param totalVolumesByParts   a list of total volumes of works by parts.
     */
    public ParameterObject(long currentVolume, double totalVolume, List<Long> currentVolumesByParts, List<Double> totalVolumesByParts) {
        this.currentVolume = currentVolume;
        this.totalVolume = totalVolume;
        this.currentVolumesByParts = currentVolumesByParts;
        this.totalVolumesByParts = totalVolumesByParts;
    }

    /**
     * Creates <code>ParameterObject</code> instance.
     *
     * @param currentVolume current volume of performed work
     * @param totalVolume   total volume of work
     */
    public ParameterObject(long currentVolume, double totalVolume) {
        this.currentVolume = currentVolume;
        this.totalVolume = totalVolume;
        this.currentVolumesByParts = new ArrayList<>();
        this.totalVolumesByParts = new ArrayList<>();
    }

    @Override
    public long getCurrentVolume() {
        return currentVolume;
    }

    @Override
    public double getTotalVolume() {
        return totalVolume;
    }

    @Override
    public List<Long> getCurrentVolumesByParts() {
        return currentVolumesByParts;
    }

    @Override
    public List<Double> getTotalVolumesByParts() {
        return totalVolumesByParts;
    }
}
