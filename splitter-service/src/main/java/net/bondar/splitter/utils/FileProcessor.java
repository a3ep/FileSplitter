package net.bondar.splitter.utils;

import net.bondar.calculations.Calculations;
import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.interfaces.Iterable;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
import net.bondar.statistics.interfaces.IStatisticService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;

/**
 * Provides process of splitting or merging file.
 */
public class FileProcessor implements IProcessor {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Name of file operation, should be "split" or "merge"
     */
    private final String fileOperation;

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
     * Cleaner thread.
     */
    private final Thread cleaner;

    /**
     * Flag for interrupting working threads.
     */
    private volatile boolean interrupt = false;

    /**
     * Process status
     */
    private String processStatus;

    /**
     * Complete file.
     */
    private File file;

    /**
     * Creates <code>FileProcessor</code> instance.
     *
     * @param partFileDest destination of the part-file
     * @param parameterHolder  parameter holder
     * @param iFactory     iterator factory
     * @param taskFactory  thread's task factory
     * @param statFactory  statistic factory
     * @see {@link IProcessor}
     */
    public FileProcessor(String partFileDest,
                         IParameterHolder parameterHolder,
                         AbstractIteratorFactory iFactory,
                         AbstractTaskFactory taskFactory,
                         AbstractStatisticFactory statFactory) {
        this.parameterHolder = parameterHolder;
        this.fileOperation = "merge";
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf(parameterHolder.getValue("partSuffix"))));
        List<File> parts = Calculations.getPartsList(file.getAbsolutePath(), parameterHolder.getValue("partSuffix"));
        this.iterator = iFactory.createMergeIterator(parts);
        this.taskFactory = taskFactory;
        this.statisticService = statFactory.createService(0, parts);
        final SynchronousQueue<Runnable> workerQueue = new SynchronousQueue<>();
        int threadsCount = Integer.parseInt(parameterHolder.getValue("threadsCount"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, workerQueue,
                new ThreadFactory() {
                    int count=1;
                    @Override
                    public Thread newThread(Runnable r) {

                        return new Thread(r, "Thread-"+count++);
                    }
                });
        cleaner = new Thread(taskFactory.createCleanTask(file, this, parameterHolder, statisticService));
    }

    /**
     * Creates <code>FileProcessor</code> instance.
     *
     * @param fileDest    the specified file destination
     * @param partSize    the specified size of the part-file
     * @param parameterHolder parameter holder
     * @param iFactory    iterator factory
     * @param taskFactory thread's task factory
     * @param statFactory statistic factory
     * @see {@link IProcessor}
     */
    public FileProcessor(String fileDest, long partSize,
                         IParameterHolder parameterHolder,
                         AbstractIteratorFactory iFactory,
                         AbstractTaskFactory taskFactory,
                         AbstractStatisticFactory statFactory) {
        this.parameterHolder = parameterHolder;
        this.fileOperation = "split";
        this.file = new File(fileDest);
        this.iterator = iFactory.createSplitIterator(parameterHolder, file.length(), partSize);
        this.taskFactory = taskFactory;
        this.statisticService = statFactory.createService(file.length(), null);
        final SynchronousQueue<Runnable> workerQueue = new SynchronousQueue<>();
        int threadsCount = Integer.parseInt(parameterHolder.getValue("threadsCount"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, workerQueue,
                new ThreadFactory() {
                    int count=1;
                    @Override
                    public Thread newThread(Runnable r) {

                        return new Thread(r, "Thread-"+count++);
                    }
                });
        cleaner = new Thread(taskFactory.createCleanTask(file, this, parameterHolder, statisticService));
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
                if (fileOperation.equals("split")) {
                    pool.execute(taskFactory.createSplitTask(file, this, parameterHolder, iterator, statisticService));
                } else {
                    pool.execute(taskFactory.createMergeTask(file, this, parameterHolder, iterator, statisticService));
                }
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            statisticService.hide();
            if (interrupt) {
                cleaner.join();
            }
            processStatus = "OK";
        } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
            log.warn("Error while showing statistical information. Message " + e.getMessage());
            throw new ApplicationException("Error while showing statistical information.",e);
        } catch (InterruptedException e) {
            log.warn("Error while waiting for closing threads. Message " + e.getMessage());
            throw new ApplicationException("Error while waiting for closing threads.", e);
        }
    }

    @Override
    public boolean getInterrupt() {
        return interrupt;
    }

    @Override
    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    @Override
    public String getProcessStatus() {
        return processStatus;
    }

    @Override
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    @Override
    public String getFileOperation() {
        return fileOperation;
    }

    @Override
    public File getFile() {
        return file;
    }
}
