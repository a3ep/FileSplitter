package net.bondar.statistics.utils;

import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.IStatObject;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Provides holding statistical data.
 */
public class StatisticsHolder implements IStatisticsHolder {

    /**
     * Map of statistics records.
     */
    private Map<String, IStatObject> records = new TreeMap<>();

    /**
     * Set with ids of statistics records.
     */
    private Set<String> recordsIds;

    /**
     * Gets all statistics records.
     *
     * @return map with statistics records
     */
    @Override
    public synchronized Map<String, IStatObject> getAllRecords() throws StatisticsException {
        recordsIds = new TreeSet<>(records.keySet());
        return records;
    }

    /**
     * Gets all ids of statistics records.
     *
     * @return set of ids of statistics records
     */
    @Override
    public synchronized Set<String> getAllRecordsIds() {
        return recordsIds;
    }

    /**
     * Gets statistics record by id.
     *
     * @param id statistics record id
     * @return statistics record
     */
    @Override
    public synchronized IStatObject getRecordById(String id) {
        return records.get(id);
    }

    /**
     * Holds record to map of records.
     *
     * @param id         statistics record id
     * @param statObject <code>IStatObject</code> object contains parameters for calculating statistical data
     */
    @Override
    public synchronized void addRecord(String id, IStatObject statObject) {
        records.put(id, statObject);
    }

    /**
     * Cleans map of statistics records.
     */
    @Override
    public synchronized void cleanRecords() {
        records.clear();
        recordsIds.clear();
    }
}
