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
     * @param currentVolume current volume of performed work
     * @param totalVolume   total volume of work
     */
    public ParameterObject(final long currentVolume, final double totalVolume, final List<ParameterObject> parameterList) {
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
    public ParameterObject(final long currentVolume, final double totalVolume) {
        this.currentVolume = currentVolume;
        this.totalVolume = totalVolume;
        this.parameterList = new ArrayList<>();
    }

    /**
     * Gets current volume of performed work.
     *
     * @return current volume of performed work
     */
    @Override
    public long getCurrentVolume() {
        return currentVolume;
    }

    /**
     * Gets total volume of work.
     *
     * @return total volume of work
     */
    @Override
    public double getTotalVolume() {
        return totalVolume;
    }

    /**
     * Gets a list of <code>IParameterObject</code>s.
     *
     * @return a list of <code>IParameterObject</code>s
     */
    @Override
    public List<ParameterObject> getParameterList() {
        return parameterList;
    }
}
