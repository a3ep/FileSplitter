package net.bondar.statistics.utils;

import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.client.IStatObject;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Provides holding statistical data.
 */
public class StatisticsHolder implements IStatisticsHolder {

    /**
     * Map with records.
     */
    private Map<String, IStatObject> records = new TreeMap<>();

    @Override
    public synchronized Map<String, IStatObject> getAllRecords() {
        return records;
    }

    @Override
    public synchronized Set<String> getAllRecordsIds() {
        return records.keySet();
    }

    @Override
    public synchronized IStatObject getRecordById(String id) {
        return records.get(id);
    }

    @Override
    public synchronized void addRecord(String id, IStatObject statObject) {
        records.put(id, statObject);
    }
}
