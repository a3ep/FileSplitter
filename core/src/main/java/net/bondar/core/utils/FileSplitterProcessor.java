package net.bondar.core.utils;

import net.bondar.calculations.FileCalculationUtils;
import net.bondar.core.exceptions.RunException;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.interfaces.factories.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.factories.AbstractIteratorFactory;
import net.bondar.core.interfaces.factories.AbstractTaskFactory;
import net.bondar.core.utils.factories.NamedThreadFactory;
import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IStatisticsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Provides process of splitting or merging file.
 */
public class FileSplitterProcessor implements IProcessor {

    /**
     * Thread name.
     */
    private static final String THREAD_NAME = "Worker";

    /**
     * Name for cleaner thread.
     */
    private static final String CLEANER_NAME = "Cleaner";

    /**
     * Part-file name suffix key.
     */
    private static final String PART_SUFFIX = "partSuffix";

    /**
     * Thread count key.
     */
    private static final String THREADS_COUNT = "threadsCount";

    /**
     * Statistics timer key.
     */
    private static final String STATISTICS_TIMER = "statisticsTimer";

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Configuration holder.
     */
    private final IConfigHolder configHolder;

    /**
     * Iterator.
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
     * Interrupts active threads and cleans temporary files.
     */
    private final Thread cleaner;

    /**
     * Processor status.
     */
    private ProcessorStatus processorStatus = ProcessorStatus.PROCESS;

    /**
     * Name of input command.
     */
    private final String commandName;

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
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf(configHolder.getValue(PART_SUFFIX))));
        this.iterator = iteratorFactory.createIterator(FileCalculationUtils.getPartsList(file.getAbsolutePath(),
                configHolder.getValue(PART_SUFFIX)));
        this.taskFactory = taskFactory;
        this.statisticsService = statisticsService;
        this.commandName = commandName;
        cleaner = new Thread(closeTaskFactory.createCloseTask(this, configHolder, THREAD_NAME), CLEANER_NAME);
        int threadsCount = Integer.parseInt(configHolder.getValue(THREADS_COUNT));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>(),
                new NamedThreadFactory());
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
        cleaner = new Thread(closeTaskFactory.createCloseTask(this, configHolder, THREAD_NAME), CLEANER_NAME);
        int threadsCount = Integer.parseInt(configHolder.getValue(THREADS_COUNT));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>(),
                new NamedThreadFactory());
    }

    /**
     * Processes file.
     *
     * @throws RunException when occurred exception during showing statistical information or splitter thread waiting for pool
     * @see {@link IProcessor}
     */
    public void process() throws RunException {
        try {
            // starts showing statistical information
            statisticsService.showStatInfo(Integer.parseInt(configHolder.getValue(STATISTICS_TIMER)));
            // creates shutdown hook with cleaner thread
            Runtime.getRuntime().addShutdownHook(cleaner);
            //distributes tasks between threads in thread pool
            for (int i = 0; i < pool.getCorePoolSize(); i++) {
                pool.execute(taskFactory.createTask(commandName, file, configHolder, iterator, statisticsService));
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            //stops showing statistical information
            statisticsService.stop();
            //checks for working cleaner
            if (cleaner.isAlive()) {
                cleaner.join();
                System.exit(0);
            }
            processorStatus = ProcessorStatus.DONE;
        } catch (StatisticsException e) {
            log.error("Error while showing statistical information. Message " + e.getMessage());
            throw new RunException("Error while showing statistical information.", e);
        } catch (InterruptedException e) {
            log.error("Error while waiting for closing threads. Message " + e.getMessage());
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
     * Gets processor status.
     *
     * @return current processor status
     */
    @Override
    public ProcessorStatus getProcessorStatus() {
        return processorStatus;
    }
}
