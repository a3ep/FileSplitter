package net.bondar.interfaces;

/**
 *
 */
public interface IStatisticService {

    /**
     *
     * @param threadName
     * @param value
     */
    void holdInformation(String threadName, long value);

    /**
     *
     * @return
     */
    String buildStatisticString();

    /**
     *
     * @param delay
     * @param period
     */
    void show(int delay, int period);

    /**
     *
     */
    void hide();
}
