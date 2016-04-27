package net.bondar.statistics.interfaces;

import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.client.IStatObject;

import java.util.Map;
import java.util.Set;

/**
 * Interface for class that provides holding statistical data.
 */
public interface IStatisticsHolder {

    /**
     * Gets all statistics records.
     *
     * @return map with statistics records
     * @throws StatisticsException if error occurred while waiting statistical data
     */
    Map<String, IStatObject> getAllRecords() throws StatisticsException;

    /**
     * Gets statistics record by id.
     *
     * @param id statistics record id
     * @return statistics record
     */
    IStatObject getRecordById(String id);

    /**
     * Holds record to map of records.
     *
     * @param id         statistics record id
     * @param statObject <code>IStatObject</code> object contains parameters for calculating statistical data
     */
    void addRecord(String id, IStatObject statObject);

    /**
     * Gets all ids of statistics records.
     *
     * @return set of ids of statistics records
     */
    Set<String> getAllRecordsIds();
}
