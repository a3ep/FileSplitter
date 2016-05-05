package net.bondar.statistics.service;

import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.*;
import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
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
     * Statistics data converter.
     */
    private final IStatisticsDataConverter dataConverter;

    /**
     * Statistics calculator.
     */
    private final IStatisticsCalculator calculator;

    /**
     * Statistics formatter.
     */
    private final IStatisticsFormatter formatter;

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
     * @param holder        statistics holder
     * @param dataConverter statistics data converter
     * @param calculator    statistics calculator
     * @param formatter     statistics formatter
     * @param viewer        statistics viewer
     */
    public StatisticsService(IStatisticsHolder holder,
                             IStatisticsDataConverter dataConverter,
                             IStatisticsCalculator calculator,
                             IStatisticsFormatter formatter,
                             IStatisticsViewer viewer) {
        this.holder = holder;
        this.dataConverter = dataConverter;
        this.calculator = calculator;
        this.formatter = formatter;
        this.viewer = viewer;
    }

    /**
     * Starts showing statistical information.
     *
     * @param period time in milliseconds between showing statistical information
     * @throws StatisticsException if error occurred while showing statistical information
     */
    @Override
    public void showStatInfo(int period) throws StatisticsException {
        statThread = new Thread(() -> {
            try {
                do {
                    viewer.showInLogs(formatter.format(calculator.calculate(dataConverter.convert(holder.getAllRecords()))));
                    Thread.sleep(period);
                } while (!Thread.interrupted());
            } catch (InterruptedException e) {
                holder.cleanRecords();
            }
        }, THREAD_NAME);
        statThread.setDaemon(true);
        statThread.start();
    }

    /**
     * Holds statistical information.
     *
     * @param id         record id
     * @param statObject object contains parameters for calculating statistical data
     */
    @Override
    public void holdInformation(final String id, final IStatObject statObject) {
        holder.addRecord(id, statObject);
    }

    /**
     * Stops of showing statistical information.
     */
    @Override
    public void stop() {
        statThread.interrupt();
    }
}
