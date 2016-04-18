package net.bondar.statistics.interfaces;

/**
 * Interface for class that provides statistical information management.
 */
public interface IStatisticService {

    /**
     * Holds information from threads into statistic information map.
     *
     * @param threadName name of the thread
     * @param filePart   <code>IPartObject</code> with parameters for calculating statistic
     */
    void holdInformation(String threadName, IPartObject filePart);

    /**
     * Builds string with statistical information.
     *
     * @return string with statistical information
     */
    String buildStatisticString();

    /**
     * Starts showing statistical information to user.
     *
     * @param delay  delay in milliseconds before task is to be executed.
     * @param period time in milliseconds between successive task executions.
     */
    void show(int delay, int period);

    /**
     * Ends showing statistical information to user.
     */
    void hide();
}
