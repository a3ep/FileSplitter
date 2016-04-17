package net.bondar.interfaces;

/**
 *
 */
public interface IStatisticService {

    /**
     *
     * @param threadName
     * @param filePart
     */
    void holdInformation(String threadName, IPart filePart);

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
