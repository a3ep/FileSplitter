package net.bondar.statistics.interfaces;

import java.util.Timer;

/**
 * Interface for class that provides working with statistical information.
 */
public interface IStatisticService {

    /**
     * Starts showing statistical information to user.
     *
     * @param delay  delay in milliseconds before task is to be executed.
     * @param period time in milliseconds between successive task executions.
     */
    void showStatisticalInfo(int delay, int period);

    /**
     * Ends showing statistical information to user.
     *
     */
    void hideStatisticalInfo();

    /**
     * Holds statistical information.
     *
     * @param information string with statistical information
     */
    void holdInformation(String information);
}
