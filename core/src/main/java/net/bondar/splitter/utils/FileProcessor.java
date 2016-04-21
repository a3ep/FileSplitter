package net.bondar.splitter.utils;

import net.bondar.calculations.Calculations;
import net.bondar.splitter.domain.CloseTask;
import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.interfaces.Iterable;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
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
     * Name of file operation.
     */
    private final String processOperation;

    /**
     * Parameter holder.
     */
    private final IParameterHolder parameterHolder;

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
     * @param statisticFactory statistic factory
     * @see {@link IProcessor}
     */
    public FileProcessor(String partFileDest, AtomicBoolean interrupt,
                         IParameterHolder parameterHolder,
                         AbstractIteratorFactory iteratorFactory,
                         AbstractTaskFactory taskFactory,
                         AbstractCloseTaskFactory closeTaskFactory,
                         AbstractStatisticFactory statisticFactory) {
        this.parameterHolder = parameterHolder;
        this.processOperation = "merge";
        this.interrupt = interrupt;
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf(parameterHolder.getValue("partSuffix"))));
        List<File> parts = Calculations.getPartsList(file.getAbsolutePath(), parameterHolder.getValue("partSuffix"));
        this.iterator = iteratorFactory.createMergeIterator(parts);
        this.taskFactory = taskFactory;
        this.statisticService = statisticFactory.createService(0, parts);
        cleaner = new Thread(closeTaskFactory.createClosable(interrupt, this, parameterHolder, statisticService));
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
     * @param statisticFactory statistic factory
     * @see {@link IProcessor}
     */
    public FileProcessor(String fileDest, long partSize, AtomicBoolean interrupt,
                         IParameterHolder parameterHolder,
                         AbstractIteratorFactory iteratorFactory,
                         AbstractTaskFactory taskFactory,
                         AbstractCloseTaskFactory closeTaskFactory,
                         AbstractStatisticFactory statisticFactory) {
        this.parameterHolder = parameterHolder;
        this.processOperation = "split";
        this.file = new File(fileDest);
        this.interrupt = interrupt;
        this.iterator = iteratorFactory.createSplitIterator(parameterHolder, file.length(), partSize);
        this.taskFactory = taskFactory;
        this.statisticService = statisticFactory.createService(file.length(), null);
        cleaner = new Thread(closeTaskFactory.createClosable(interrupt, this, parameterHolder, statisticService));
        int threadsCount = Integer.parseInt(parameterHolder.getValue("threadsCount"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
    }

    /**
     * Processes file.
     *
     * @throws ApplicationException when occurred exception during showing statistical information or main thread waiting for pool
     * @see {@link IProcessor}
     */
    public void process() throws ApplicationException {
        try {
            statisticService.show(0, 1000);
            Runtime.getRuntime().addShutdownHook(cleaner);
            for (int i = 0; i < pool.getCorePoolSize(); i++) {
                if (processOperation.equals("split")) {
                    pool.execute(taskFactory.createSplitTask(file, interrupt, parameterHolder, iterator, statisticService));
                } else {
                    pool.execute(taskFactory.createMergeTask(file, interrupt, parameterHolder, iterator, statisticService));
                }
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            statisticService.hide();
            if (interrupt.get()) {
                cleaner.join();
                System.exit(0);
            }
            processStatus = "OK";
        } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
            log.warn("Error while showing statistical information. Message " + e.getMessage());
            throw new ApplicationException("Error while showing statistical information.", e);
        } catch (InterruptedException e) {
            log.warn("Error while waiting for closing threads. Message " + e.getMessage());
            throw new ApplicationException("Error while waiting for closing threads.", e);
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String getProcessOperation() {
        return processOperation;
    }

    @Override
    public String getProcessStatus() {
        return processStatus;
    }
}
