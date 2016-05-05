package net.bondar.statistics.interfaces;

import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.client.IStatObject;

/**
 * Provides working with statistical information.
 */
public interface IStatisticsService {

    /**
     * Starts showing statistical information to user.
     *
     * @param period time in milliseconds between showing statistical information
     * @throws StatisticsException if error occurred while showing statistical information
     */
    void showStatInfo(final int period) throws StatisticsException;

    /**
     * Holds statistical information.
     *
     * @param id         record id
     * @param statObject object contains parameters for calculating statistical data
     */
    void holdInformation(final String id, final IStatObject statObject);

    /**
     * Stops of showing statistical information.
     */
    void stop();
}
