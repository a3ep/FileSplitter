package net.bondar.core.utils;

import net.bondar.core.exceptions.RunException;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.utils.factories.CloseTaskFactory;
import net.bondar.core.utils.factories.IteratorFactory;
import net.bondar.core.utils.factories.NamedThreadFactory;
import net.bondar.core.utils.factories.TaskFactory;
import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IStatisticsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Provides process of splitting or merging file.
 */
public class FileProcessor {

    /**
     * Name for cleaner thread.
     */
    private static final String CLEANER_NAME = "Cleaner";

    /**
     * Part-file name suffix key for configuration holder.
     */
    private static final String PART_SUFFIX = "partSuffix";

    /**
     * Thread count key for configuration holder.
     */
    private static final String THREADS_COUNT = "threadsCount";

    /**
     * Statistics timer key for configuration holder.
     */
    private static final String STATISTICS_TIMER = "statisticsTimer";

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Configuration holder.
     */
    private final ConfigHolder configHolder;

    /**
     * Iterator.
     */
    private final Iterable iterator;

    /**
     * Thread pool.
     */
    private final ThreadPoolExecutor pool;

    /**
     * Task factory.
     */
    private final TaskFactory taskFactory;

    /**
     * Close task factory.
     */
    private final CloseTaskFactory closeTaskFactory;

    /**
     * Statistic service.
     */
    private final IStatisticsService statisticsService;

    /**
     * Processor status.
     */
    private ProcessorStatus processorStatus = ProcessorStatus.PROCESS;

    /**
     * Complete file.
     */
    private File file;

    /**
     * List of part-files.
     */
    private List<File> files = new ArrayList<>();

    /**
     * Creates <code>FileProcessor</code> instance.
     *
     * @param partFileDest      destination of the part-file
     * @param configHolder      configuration holder
     * @param iteratorFactory   iterator factory
     * @param taskFactory       thread's task factory
     * @param closeTaskFactory  close task factory
     * @param statisticsService statistics service
     */
    public FileProcessor(final String partFileDest,
                         ConfigHolder configHolder,
                         IteratorFactory iteratorFactory,
                         TaskFactory taskFactory,
                         CloseTaskFactory closeTaskFactory,
                         IStatisticsService statisticsService) {
        this.configHolder = configHolder;
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf(configHolder.getValue(PART_SUFFIX))));
        this.files = FilesFinder.getPartsList(file.getAbsolutePath(), configHolder.getValue(PART_SUFFIX));
        long fileLength = 0;
        for (File file : files) {
            fileLength += file.length();
        }
        this.iterator = iteratorFactory.createIterator(configHolder, fileLength + files.size() - 1, files.get(0).length());
        this.taskFactory = taskFactory;
        this.closeTaskFactory = closeTaskFactory;
        this.statisticsService = statisticsService;
        int threadsCount = Integer.parseInt(configHolder.getValue(THREADS_COUNT));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>(),
                new NamedThreadFactory());
    }

    /**
     * Creates <code>FileProcessor</code> instance.
     *
     * @param fileDest          the specified file destination
     * @param partSize          the specified size of the part-file
     * @param configHolder      configuration holder
     * @param iteratorFactory   iterator factory
     * @param taskFactory       thread's task factory
     * @param closeTaskFactory  closing task factory
     * @param statisticsService statistics service
     */
    public FileProcessor(final String fileDest, final long partSize,
                         ConfigHolder configHolder,
                         IteratorFactory iteratorFactory,
                         TaskFactory taskFactory,
                         CloseTaskFactory closeTaskFactory,
                         IStatisticsService statisticsService) {
        this.configHolder = configHolder;
        this.file = new File(fileDest);
        this.iterator = iteratorFactory.createIterator(configHolder, file.length(), partSize);
        this.taskFactory = taskFactory;
        this.closeTaskFactory = closeTaskFactory;
        this.statisticsService = statisticsService;
        int threadsCount = Integer.parseInt(configHolder.getValue(THREADS_COUNT));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>(),
                new NamedThreadFactory());
    }

    /**
     * Processes file.
     *
     * @return true if process ok, otherwise false
     * @throws RunException when occurred exception during showing statistical information or splitter thread waiting for pool
     */
    public boolean process() throws RunException {
        log.info("Start processing. Processor status: " + processorStatus.name());
        try {
            // starts showing statistical information
            statisticsService.showStatInfo(Integer.parseInt(configHolder.getValue(STATISTICS_TIMER)));
            // creates list of futures that represented threads in a pool
            List<Future> futures = new ArrayList<>();
            // creates shutdown hook with cleaner thread
            Thread cleaner;
            Runtime.getRuntime().addShutdownHook(cleaner = new Thread(closeTaskFactory
                    .createCloseTask(this, futures), CLEANER_NAME));
            log.info("Start creating tasks...");
            //distributes tasks between threads in thread pool
            for (int i = 0; i < pool.getCorePoolSize(); i++) {
                futures.add(pool.submit(taskFactory.createTask(file, files, configHolder, iterator, statisticsService)));
            }
            log.info("Initializing shutdown thread pool.");
            pool.shutdown();
            log.info("Waiting for thread pool termination...");
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            //stops showing statistical information
            statisticsService.stop();
            //checks for working cleaner
            if (cleaner.isAlive()) {
                cleaner.join();
                processorStatus = ProcessorStatus.TERMINATED;
                log.info("Processing was terminated. Processor status: " + processorStatus.name());
                return false;
            }
            processorStatus = ProcessorStatus.DONE;
            log.info("Finish processing. Processor status: " + processorStatus.name());
            return true;
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
    public File getFile() {
        return file;
    }

    /**
     * Gets processor status.
     *
     * @return current processor status
     */
    public ProcessorStatus getProcessorStatus() {
        return processorStatus;
    }
}
