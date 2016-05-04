package net.bondar.core.utils;

import net.bondar.calculations.FileCalculationUtils;
import net.bondar.core.exceptions.RunException;
import net.bondar.core.interfaces.*;
import net.bondar.core.interfaces.Iterable;
import net.bondar.statistics.interfaces.IStatisticsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides process of splitting or merging file.
 */
public class FileSplitterProcessor implements IProcessor {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Configuration holder.
     */
    private final IConfigHolder configHolder;

    /**
     * Merge iterator.
     */
    private final Iterable iterator;

    /**
     * Thread pool.
     */
    private final ThreadPoolExecutor pool;

    /**
     * Thread's task factory.
     */
    private final AbstractTaskFactory taskFactory;

    /**
     * Statistic service.
     */
    private final IStatisticsService statisticsService;

    /**
     * Cleans temporary files and closes application.
     */
    private final Thread cleaner;

    /**
     * Name of input command.
     */
    private final String commandName;

    /**
     * Flag for interrupting working threads.
     */
    private AtomicBoolean interrupt = new AtomicBoolean(false);

    /**
     * Process status
     */
    private String processStatus = "";

    /**
     * Complete file.
     */
    private File file;

    /**
     * Creates <code>FileSplitterProcessor</code> instance.
     *
     * @param partFileDest      destination of the part-file
     * @param configHolder      configuration holder
     * @param iteratorFactory   iterator factory
     * @param taskFactory       thread's task factory
     * @param closeTaskFactory  close task factory
     * @param statisticsService statistics service
     * @param commandName       name of input command
     * @see {@link IProcessor}
     */
    public FileSplitterProcessor(String partFileDest,
                                 IConfigHolder configHolder,
                                 AbstractIteratorFactory iteratorFactory,
                                 AbstractTaskFactory taskFactory,
                                 AbstractCloseTaskFactory closeTaskFactory,
                                 IStatisticsService statisticsService,
                                 String commandName) {
        this.configHolder = configHolder;
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf(configHolder.getValue("partSuffix"))));
        List<File> parts = FileCalculationUtils.getPartsList(file.getAbsolutePath(), configHolder.getValue("partSuffix"));
        this.iterator = iteratorFactory.createIterator(parts);
        this.taskFactory = taskFactory;
        this.statisticsService = statisticsService;
        this.commandName = commandName;
        cleaner = new Thread(closeTaskFactory.createCloseTask(interrupt, this, configHolder));
        int threadsCount = Integer.parseInt(configHolder.getValue("threadsCount"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
    }

    /**
     * Creates <code>FileSplitterProcessor</code> instance.
     *
     * @param fileDest          the specified file destination
     * @param partSize          the specified size of the part-file
     * @param configHolder      configuration holder
     * @param iteratorFactory   iterator factory
     * @param taskFactory       thread's task factory
     * @param closeTaskFactory  closing task factory
     * @param statisticsService statistics service
     * @param commandName       name of input command
     * @see {@link IProcessor}
     */
    public FileSplitterProcessor(String fileDest, long partSize,
                                 IConfigHolder configHolder,
                                 AbstractIteratorFactory iteratorFactory,
                                 AbstractTaskFactory taskFactory,
                                 AbstractCloseTaskFactory closeTaskFactory,
                                 IStatisticsService statisticsService,
                                 String commandName) {
        this.configHolder = configHolder;
        this.file = new File(fileDest);
        this.iterator = iteratorFactory.createIterator(configHolder, file.length(), partSize);
        this.taskFactory = taskFactory;
        this.statisticsService = statisticsService;
        this.commandName = commandName;
        cleaner = new Thread(closeTaskFactory.createCloseTask(interrupt, this, configHolder));
        int threadsCount = Integer.parseInt(configHolder.getValue("threadsCount"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
    }

    /**
     * Processes file.
     *
     * @throws RunException when occurred exception during showing statistical information or splitter thread waiting for pool
     * @see {@link IProcessor}
     */
    public void process() throws RunException {
        try {
            statisticsService.showStatInfo(Integer.parseInt(configHolder.getValue("statisticsTimer")));
            Runtime.getRuntime().addShutdownHook(cleaner);
            for (int i = 0; i < pool.getCorePoolSize(); i++) {
                pool.execute(taskFactory.createTask(commandName, file, interrupt, configHolder, iterator, statisticsService));
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            statisticsService.stop();
            if (interrupt.get()) {
                cleaner.join();
                System.exit(0);
            }
            processStatus = "OK";
        } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
            log.warn("Error while showing statistical information. Message " + e.getMessage());
            throw new RunException("Error while showing statistical information.", e);
        } catch (InterruptedException e) {
            log.warn("Error while waiting for closing threads. Message " + e.getMessage());
            throw new RunException("Error while waiting for closing threads.", e);
        }
    }

    /**
     * Gets the complete file.
     *
     * @return complete file
     */
    @Override
    public File getFile() {
        return file;
    }

    /**
     * Gets name of executing command.
     *
     * @return name of executing command
     */
    @Override
    public String getCommandName() {
        return commandName;
    }

    /**
     * Gets process status.
     *
     * @return current process status
     */
    @Override
    public String getProcessStatus() {
        return processStatus;
    }
}
