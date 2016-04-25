package net.bondar.core.utils;

import net.bondar.calculations.FileCalculationUtils;
import net.bondar.core.exceptions.RunException;
import net.bondar.core.interfaces.*;
import net.bondar.core.interfaces.Iterable;
import net.bondar.statistics.interfaces.IStatisticService;
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
public class FileProcessor implements IProcessor {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parameter holder.
     */
    private final IConfigHolder parameterHolder;

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
    private final IStatisticService statisticService;

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
    private AtomicBoolean interrupt;

    /**
     * Process status
     */
    private String processStatus = "";

    /**
     * Complete file.
     */
    private File file;

    /**
     * Creates <code>FileProcessor</code> instance.
     *
     * @param partFileDest     destination of the part-file
     * @param parameterHolder  parameter holder
     * @param iteratorFactory  iterator factory
     * @param taskFactory      thread's task factory
     * @param closeTaskFactory close task factory
     * @param statisticService statistic service
     * @param commandName      name of input command
     * @see {@link IProcessor}
     */
    public FileProcessor(String partFileDest, AtomicBoolean interrupt,
                         IConfigHolder parameterHolder,
                         AbstractIteratorFactory iteratorFactory,
                         AbstractTaskFactory taskFactory,
                         AbstractCloseTaskFactory closeTaskFactory,
                         IStatisticService statisticService,
                         String commandName) {
        this.parameterHolder = parameterHolder;
        this.interrupt = interrupt;
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf(parameterHolder.getValue("partSuffix"))));
        List<File> parts = FileCalculationUtils.getPartsList(file.getAbsolutePath(), parameterHolder.getValue("partSuffix"));
        this.iterator = iteratorFactory.createMergeIterator(parts);
        this.taskFactory = taskFactory;
        this.statisticService = statisticService;
        this.commandName = commandName;
        cleaner = new Thread(closeTaskFactory.createCloseTask(interrupt, this, parameterHolder, statisticService));
        int threadsCount = Integer.parseInt(parameterHolder.getValue("threadsCount"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
    }

    /**
     * Creates <code>FileProcessor</code> instance.
     *
     * @param fileDest         the specified file destination
     * @param partSize         the specified size of the part-file
     * @param parameterHolder  parameter holder
     * @param iteratorFactory  iterator factory
     * @param taskFactory      thread's task factory
     * @param closeTaskFactory closing task factory
     * @param statisticService statistic service
     * @param commandName      name of input command
     * @see {@link IProcessor}
     */
    public FileProcessor(String fileDest, long partSize, AtomicBoolean interrupt,
                         IConfigHolder parameterHolder,
                         AbstractIteratorFactory iteratorFactory,
                         AbstractTaskFactory taskFactory,
                         AbstractCloseTaskFactory closeTaskFactory,
                         IStatisticService statisticService,
                         String commandName) {
        this.parameterHolder = parameterHolder;
        this.file = new File(fileDest);
        this.interrupt = interrupt;
        this.iterator = iteratorFactory.createSplitIterator(parameterHolder, file.length(), partSize);
        this.taskFactory = taskFactory;
        this.statisticService = statisticService;
        this.commandName = commandName;
        cleaner = new Thread(closeTaskFactory.createCloseTask(interrupt, this, parameterHolder, statisticService));
        int threadsCount = Integer.parseInt(parameterHolder.getValue("threadsCount"));
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
            statisticService.showStatisticalInfo(0, 1000);
            Runtime.getRuntime().addShutdownHook(cleaner);
            for (int i = 0; i < pool.getCorePoolSize(); i++) {
                if (commandName.equalsIgnoreCase("split")) {
                    pool.execute(taskFactory.createSplitTask(file, interrupt, parameterHolder, iterator, statisticService));
                } else {
                    pool.execute(taskFactory.createMergeTask(file, interrupt, parameterHolder, iterator, statisticService));
                }
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            statisticService.hideStatisticalInfo();
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

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getProcessStatus() {
        return processStatus;
    }
}
