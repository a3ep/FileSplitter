package net.bondar.statistics.service;

import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IStatisticsHolder;
import net.bondar.statistics.interfaces.IStatisticsService;
import net.bondar.statistics.interfaces.IStatisticsViewer;
import net.bondar.statistics.interfaces.client.IStatObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

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
    public void showStatInfo(AtomicBoolean disable, int delay, int period) throws IllegalArgumentException, IllegalStateException, NullPointerException {
        Thread statThread = new Thread(() -> {
            try {
                Thread.sleep(delay);
                while (!disable.get()) {
                    viewer.showInLogs(disable);
                    Thread.sleep(period);
                }
            } catch (InterruptedException e) {
                log.error("Error while showing statistical data. Message: " + e.getMessage());
                throw new StatisticsException("Error while showing statistical data.", e);
            }
        }, THREAD_NAME);
        statThread.setDaemon(true);
        statThread.start();
    }

    @Override
    public void holdInformation(String id, IStatObject statObject) {
        holder.addRecord(id, statObject);
    }
}
