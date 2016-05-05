package net.bondar.statistics.service;

import net.bondar.statistics.interfaces.IParameterObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
     * List of statistics records ids.
     */
    private final List<String> listOfIds;

    /**
     * List of <code>ParameterObject</code>s.
     */
    private final List<ParameterObject> parameterList;

    /**
     * Creates <code>ParameterObject</code> instance.
     *
     * @param setOfIds      list of statistics records ids
     * @param currentVolume current volume of performed work
     * @param totalVolume   total volume of work
     */
    public ParameterObject(final Set<String> setOfIds, final long currentVolume, final double totalVolume, final List<ParameterObject> parameterList) {
        this.listOfIds = new ArrayList<>(setOfIds);
        Collections.sort(listOfIds);
        this.currentVolume = currentVolume;
        this.totalVolume = totalVolume;
        this.parameterList = parameterList;
    }

    /**
     * Creates <code>ParameterObject</code> instance.
     *
     * @param setOfIds      list of statistics records ids
     * @param currentVolume current volume of performed work
     * @param totalVolume   total volume of work
     */
    public ParameterObject(final Set<String> setOfIds, final long currentVolume, final double totalVolume) {
        this.listOfIds = new ArrayList<>(setOfIds);
        Collections.sort(listOfIds);
        this.currentVolume = currentVolume;
        this.totalVolume = totalVolume;
        this.parameterList = new ArrayList<>();
    }

    /**
     * Gets list of statistics records ids.
     *
     * @return list of statistics records ids
     */
    @Override
    public List<String> getListOfIds() {
        return listOfIds;
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

    @Override
    public String toString() {
        return "ParameterObject{" +
                "currentVolume=" + currentVolume +
                ", totalVolume=" + totalVolume +
                ", parameterList=" + parameterList +
                '}';
    }
}
