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
     * Gets all records.
     *
     * @return map with records
     * @throws StatisticsException if error occurred while waiting statistical data
     */
    Map<String, IStatObject> getAllRecords();

    /**
     * Gets record by id
     *
     * @param id record id
     * @return current record
     */
    IStatObject getRecordById(String id);

    /**
     * Holds record to map of records.
     *
     * @param id         record id
     * @param statObject object contains parameters for calculating statistical data
     */
    void addRecord(String id, IStatObject statObject);

    /**
     * Gets all ids of records.
     *
     * @return set of ids of records
     */
    Set<String> getAllRecordsIds();
}
