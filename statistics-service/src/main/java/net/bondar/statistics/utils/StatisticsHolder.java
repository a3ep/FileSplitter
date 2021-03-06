package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.client.IStatObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Provides holding statistical data.
 */
public class StatisticsHolder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Map with statistics records.
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
    public synchronized Map<String, IStatObject> getAllRecords() {
        if (!records.isEmpty()) {
            recordsIds = new TreeSet<>(records.keySet());
        }
        return records;
    }

    /**
     * Gets all ids of statistics records.
     *
     * @return set of ids of statistics records
     */
    public synchronized Set<String> getAllRecordsIds() {
        return recordsIds;
    }

    /**
     * Gets statistics record by id.
     *
     * @param id statistics record id
     * @return statistics record
     */
    public synchronized IStatObject getRecordById(final String id) {
        return records.get(id);
    }

    /**
     * Holds record to map with statistics records.
     *
     * @param id         statistics record id
     * @param statObject <code>IStatObject</code> object contains parameters for calculating statistical data
     */
    public synchronized void addRecord(final String id, final IStatObject statObject) {
        records.put(id, statObject);
    }

    /**
     * Cleans map with statistics records.
     */
    public synchronized void cleanRecords() {
        records.clear();
        recordsIds.clear();
    }
}
