package net.bondar.statistics.service;

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
     * List of <code>ParameterObject</code>s.
     */
    private final List<ParameterObject> parameterList;

    /**
     * Creates <code>ParameterObject</code> instance.
     *
     * @param currentVolume         current volume of performed work
     * @param totalVolume           total volume of work
     */
    public ParameterObject(long currentVolume, double totalVolume, List<ParameterObject> parameterList) {
        this.currentVolume = currentVolume;
        this.totalVolume = totalVolume;
        this.parameterList = parameterList;
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
        this.parameterList = new ArrayList<>();
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
    public List<ParameterObject> getParameterList() {
        return parameterList;
    }
}
