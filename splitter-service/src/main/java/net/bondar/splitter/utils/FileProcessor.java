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
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private final IParameterHolder paramHolder;

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
    private final IStatisticService statService;

    /**
     * Flag for interrupting working threads.
     */
    private volatile boolean interrupt = false;

    /**
     * Process status
     */
    private String processStatus = "";

    /**
     * Complete file.
     */
    private File file;

    /**
     * Cleaner thread.
     */
    private Thread cleaner;

    /**
     * Creates <code>FileProcessor</code> instance.
     *
     * @param partFileDest destination of the part-file
     * @param paramHolder  parameter holder
     * @param iFactory     iterator factory
     * @param tFactory     thread factory
     * @param taskFactory  thread's task factory
     * @param statFactory  statistic factory
     * @see {@link IProcessor}
     */
    public FileProcessor(String partFileDest,
                         IParameterHolder paramHolder,
                         AbstractIteratorFactory iFactory,
                         AbstractThreadFactory tFactory,
                         AbstractTaskFactory taskFactory,
                         AbstractStatisticFactory statFactory) {
        this.paramHolder = paramHolder;
        this.fileOperation = "merge";
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf(paramHolder.getValue("partSuffix"))));
        List<File> parts = Calculations.getPartsList(file.getAbsolutePath(), paramHolder.getValue("partSuffix"));
        this.iterator = iFactory.createMergeIterator(parts);
        this.taskFactory = taskFactory;
        this.statService = statFactory.createService(0, parts);
        final SynchronousQueue<Runnable> workerQueue = new SynchronousQueue<>();
        int threadsCount = Integer.parseInt(paramHolder.getValue("threadsCount"));
        tFactory.setThreadName(paramHolder.getValue("threadName"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, workerQueue, tFactory, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    pool.getQueue().put(r);
                } catch (InterruptedException e) {
                    log.warn("Catches InterruptedException, during putting tasks into queue. Message " + e.getMessage());
                    throw new ApplicationException("Error during putting tasks into queue. Exception:" + e.getMessage());
                }
            }
        });
        cleaner = new Thread(taskFactory.createCleanTask(file, this, paramHolder, statService));
    }

    /**
     * Creates <code>FileProcessor</code> instance.
     *
     * @param fileDest    the specified file destination
     * @param partSize    the specified size of the part-file
     * @param paramHolder parameter holder
     * @param iFactory    iterator factory
     * @param tFactory    thread factory
     * @param taskFactory thread's task factory
     * @param statFactory statistic factory
     * @see {@link IProcessor}
     */
    public FileProcessor(String fileDest, long partSize,
                         IParameterHolder paramHolder,
                         AbstractIteratorFactory iFactory,
                         AbstractThreadFactory tFactory,
                         AbstractTaskFactory taskFactory,
                         AbstractStatisticFactory statFactory) {
        this.paramHolder = paramHolder;
        this.fileOperation = "split";
        this.file = new File(fileDest);
        this.iterator = iFactory.createSplitIterator(paramHolder, file.length(), partSize);
        this.taskFactory = taskFactory;
        this.statService = statFactory.createService(file.length(), null);
        final SynchronousQueue<Runnable> workerQueue = new SynchronousQueue<>();
        int threadsCount = Integer.parseInt(paramHolder.getValue("threadsCount"));
        tFactory.setThreadName(paramHolder.getValue("threadName"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, workerQueue, tFactory, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    pool.getQueue().put(r);
                } catch (InterruptedException e) {
                    log.warn("Catches InterruptedException, during putting tasks into queue. Message " + e.getMessage());
                    throw new ApplicationException("Error during putting tasks into queue. Exception:" + e.getMessage());
                }
            }
        });
        cleaner = new Thread(taskFactory.createCleanTask(file, this, paramHolder, statService));
    }

    /**
     * Processes file.
     *
     * @throws ApplicationException when occurred exception during showing statistical information or main thread waiting for pool
     * @see {@link IProcessor}
     */
    public void process() throws ApplicationException{
        try {
            statService.show(0, 1000);
            Runtime.getRuntime().addShutdownHook(cleaner);
            for (int i = 0; i < pool.getCorePoolSize(); i++) {
                if (fileOperation.equals("split")) {
                    pool.execute(taskFactory.createSplitTask(file, this, paramHolder, iterator, statService));
                } else {
                    pool.execute(taskFactory.createMergeTask(file, this, paramHolder, iterator, statService));
                }
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            statService.hide();
            if (interrupt) {
                cleaner.join();
                System.exit(0);
            }
            processStatus = "OK";
        } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
            log.warn("Catches exception, during showing statistical information. Message " + e.getMessage());
            throw new ApplicationException("Error during showing statistical information. Exception:" + e.getMessage());
        } catch (InterruptedException e) {
            log.warn("Catches InterruptedException, during main thread waiting for pool. Message " + e.getMessage());
            throw new ApplicationException("Error during main thread waiting. Exception:" + e.getMessage());
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
