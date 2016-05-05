package net.bondar.statistics.service;

import net.bondar.statistics.interfaces.IDataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides holding calculated statistical data.
 */
public class DataObject implements IDataObject{

    /**
     * Total progress value.
     */
    private final double totalProgress;

    /**
     * List of parts progress value.
     */
    private final List<Double> partsProgress;

    /**
     * Time remaining value.
     */
    private final double timeRemaining;

    /**
     * List of statistics records ids.
     */
    private final List<String> listOfIds;

    public DataObject(List<String> listOfIds, double totalProgress, List<Double> partsProgress, double timeRemaining) {
        this.listOfIds = listOfIds;
        this.totalProgress = totalProgress;
        this.partsProgress = partsProgress;
        this.timeRemaining = timeRemaining;
    }

    public DataObject(List<String> listOfIds, double totalProgress, double timeRemaining){
        this.listOfIds = listOfIds;
        this.totalProgress = totalProgress;
        this.partsProgress = new ArrayList<>();
        this.timeRemaining = timeRemaining;
    }

    /**
     * Gets total progress.
     *
     * @return total progress value
     */
    @Override
    public double getTotalProgress() {
        return totalProgress;
    }

    /**
     * Gets list of parts progress.
     *
     * @return kist of parts progress value
     */
    public List<Double> getPartsProgress() {
        return partsProgress;
    }

    /**
     * Gets time remaining.
     *
     * @return time remaining value
     */
    @Override
    public double getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Gets list of statitics records ids.
     *
     * @return list of statistics records ids
     */
    @Override
    public List<String> getListOfIds() {
        return listOfIds;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "totalProgress=" + totalProgress +
                ", partsProgress=" + partsProgress +
                ", timeRemaining=" + timeRemaining +
                ", listOfIds=" + listOfIds +
                '}';
    }
}
