package net.bondar.statistics.service;

import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.IStatisticsService;
import net.bondar.statistics.interfaces.IStatisticsViewer;
import net.bondar.statistics.interfaces.client.IStatObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides processing of statistical data.
 */
public class StatisticsService implements IStatisticsService {

    /**
     * Statistics thread name.
     */
    private static final String THREAD_NAME = "StatisticsThread";

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Statistics holder.
     */
    private final IStatisticsHolder holder;

    /**
     * Statistics viewer.
     */
    private final IStatisticsViewer viewer;

    /**
     * Thread for processing showing statistical information.
     */
    private Thread statThread;

    /**
     * Creates <code>StatisticsService</code> instance.
     *
     * @param holder statistics holder
     * @param viewer statistics viewer
     */
    public StatisticsService(IStatisticsHolder holder, IStatisticsViewer viewer) {
        this.holder = holder;
        this.viewer = viewer;
    }

    @Override
    public void showStatInfo(int period) throws StatisticsException {
        statThread = new Thread(() -> {
            try {
                do {
                    viewer.showInLogs();
                    Thread.sleep(period);
                } while (!Thread.interrupted());
                holder.cleanRecords();
            } catch (InterruptedException e) {
                log.warn("Error while showing statistical data. Message: " + e.getMessage());
            }
        }, THREAD_NAME);
        statThread.setDaemon(true);
        statThread.start();
    }

    @Override
    public void holdInformation(String id, IStatObject statObject) {
        holder.addRecord(id, statObject);
    }

    @Override
    public void stop() {
        statThread.interrupt();
    }
}
