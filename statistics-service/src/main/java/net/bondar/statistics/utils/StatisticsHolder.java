package net.bondar.statistics.utils;

import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IStatisticsHolder;
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
public class StatisticsHolder implements IStatisticsHolder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Map with records.
     */
    private Map<String, IStatObject> records = new TreeMap<>();

    /**
     * Set with ids of records.
     */
    private Set<String> recordsIds;

    @Override
    public synchronized Map<String, IStatObject> getAllRecords() throws StatisticsException {
        try {
            while (records.isEmpty()) {
                wait();
            }
        } catch (InterruptedException e) {
            log.error("Error while waiting for statistical data. MessageL: " + e.getMessage());
            throw new StatisticsException("Error while waiting for statistical data.", e);
        }
        recordsIds = new TreeSet<>(records.keySet());
        return records;
    }

    @Override
    public synchronized Set<String> getAllRecordsIds() {
        return recordsIds;
    }

    @Override
    public synchronized IStatObject getRecordById(String id) {
        return records.get(id);
    }

    @Override
    public synchronized void addRecord(String id, IStatObject statObject) {
        records.put(id, statObject);
        notifyAll();
    }

    public synchronized void cleanRecords() {
        records.clear();
        recordsIds.clear();
    }
}
