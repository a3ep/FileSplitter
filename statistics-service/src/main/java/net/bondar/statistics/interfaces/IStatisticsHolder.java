package net.bondar.statistics.interfaces;

import net.bondar.statistics.interfaces.client.IStatObject;

import java.util.Map;
import java.util.Set;

/**
 * Provides holding statistical data.
 */
public interface IStatisticsHolder {

    /**
     * Gets all statistics records.
     *
     * @return map with statistics records
     */
    Map<String, IStatObject> getAllRecords();

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
    void addRecord(final String id, final IStatObject statObject);

    /**
     * Gets all ids of statistics records.
     *
     * @return set of ids of statistics records
     */
    Set<String> getAllRecordsIds();

    /**
     * Cleans statistics records.
     */
    void cleanRecords();
}
