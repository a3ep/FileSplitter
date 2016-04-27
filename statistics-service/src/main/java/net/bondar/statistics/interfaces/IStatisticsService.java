package net.bondar.statistics.interfaces;

import net.bondar.statistics.interfaces.client.IStatObject;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface for class that provides working with statistical information.
 */
public interface IStatisticsService {

    /**
     * Starts showing statistical information to user.
     *
     * @param disable flag for cancellation information display
     * @param delay   delay in milliseconds before task is to be executed.
     * @param period  time in milliseconds between successive task executions.
     */
    void showStatInfo(AtomicBoolean disable, int delay, int period);

    /**
     * Holds statistical information.
     *
     * @param id         record id
     * @param statObject object contains parameters for calculating statistical data
     */
    void holdInformation(String id, IStatObject statObject);
}
