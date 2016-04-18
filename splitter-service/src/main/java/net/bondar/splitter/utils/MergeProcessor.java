package net.bondar.splitter.utils;

import net.bondar.calculations.Calculations;
import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.interfaces.Iterable;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
import net.bondar.statistics.interfaces.IStatisticService;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Provides process of merging files.
 */
public class MergeProcessor implements IProcessor {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Destination of the part-file.
     */
    private final String partFileDest;

    /**
     * Complete file.
     */
    private final File file;

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
    private AbstractTaskFactory taskFactory;

    /**
     * Statistic service.
     */
    private IStatisticService statService;

    /**
     * Creates <code>MergeProcessor</code> instance.
     *
     * @param partFileDest destination of the part-file
     * @param paramHolder  parameter holder
     * @param iFactory     iterator factory
     * @param tFactory     thread factory
     * @param taskFactory  thread's task factory
     * @param statFactory  statistic factory
     */
    public MergeProcessor(String partFileDest,
                          IParameterHolder paramHolder,
                          AbstractIteratorFactory iFactory,
                          AbstractThreadFactory tFactory,
                          AbstractTaskFactory taskFactory,
                          AbstractStatisticFactory statFactory) {
        this.paramHolder = paramHolder;
        this.partFileDest = partFileDest;
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf(paramHolder.getValue("partSuffix"))));
        this.iterator = iFactory.createMergeIterator(Calculations.getPartsList(partFileDest, paramHolder.getValue("partSuffix")));
        this.taskFactory = taskFactory;
        this.statService = statFactory.createService(0, Calculations.getPartsList(partFileDest, paramHolder.getValue("partSuffix")));
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
     * Processes merging files.
     */
    @Override
    public void process() {
        statService.show(0, 1000);
        for (int i = 0; i < pool.getCorePoolSize(); i++) {
            pool.execute(taskFactory.createMergeTask(file, paramHolder, iterator, statService));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.warn("Catches InterruptedException, during main thread waiting for pool. Message " + e.getMessage());
            statService.hide();
            throw new ApplicationException("Error during main thread sleeping. Exception:" + e.getMessage());
        }
        statService.hide();
    }
}
