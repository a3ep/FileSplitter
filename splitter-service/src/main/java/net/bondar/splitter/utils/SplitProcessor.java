package net.bondar.splitter.utils;

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
 * Provides process of splitting file.
 */
public class SplitProcessor implements IProcessor {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Specified file.
     */
    private final File file;

    /**
     * Parameter holder.
     */
    private final IParameterHolder paramHolder;

    /**
     * Split iterator.
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
    private IStatisticService statService;

    /**
     * Creates <code>SplitProcessor</code> instance.
     *
     * @param fileDest    the specified file destination
     * @param partSize    the specified size of the part-file
     * @param paramHolder parameter holder
     * @param iFactory    iterator factory
     * @param tFactory    thread factory
     * @param taskFactory thread's task factory
     * @param statFactory statistic factory
     */
    public SplitProcessor(String fileDest, long partSize,
                          IParameterHolder paramHolder,
                          AbstractIteratorFactory iFactory,
                          AbstractThreadFactory tFactory,
                          AbstractTaskFactory taskFactory,
                          AbstractStatisticFactory statFactory) {
        this.paramHolder = paramHolder;
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
     * Processes splitting file.
     */
    @Override
    public void process() {
        statService.show(0, 1000);
        for (int i = 0; i < pool.getCorePoolSize(); i++) {
            pool.execute(taskFactory.createSplitTask(file, paramHolder, iterator, statService));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.warn("Catches InterruptedException, during main thread waiting for pool. Message " + e.getMessage());
            throw new ApplicationException("Error during main thread sleeping. Exception:" + e.getMessage());
        }
        statService.hide();
    }
}
