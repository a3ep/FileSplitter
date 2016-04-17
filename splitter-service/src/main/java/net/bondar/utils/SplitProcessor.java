package net.bondar.utils;

import net.bondar.domain.FilePart;
import net.bondar.exceptions.ApplicationException;
import net.bondar.interfaces.*;
import net.bondar.interfaces.Iterable;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class SplitProcessor implements IProcessor {

    /**
     *
     */
    private final Logger log = Logger.getLogger("splitterLogger");

    /**
     *
     */
    private final File file;

    /**
     *
     */
    private final IParameterHolder parameterHolder;

    /**
     *
     */
    private final Iterable iterator;

    /**
     *
     */
    private final ThreadPoolExecutor pool;

    /**
     *
     */
    private final AbstractTaskFactory runnableFactory;
    /**
     *
     */
    private IStatisticService statisticService;

    /**
     * @param fileDest
     * @param partSize
     * @param parameterHolder
     * @param iteratorFactory
     * @param threadFactory
     * @param runnableFactory
     * @param statisticFactory
     */
    public SplitProcessor(String fileDest, long partSize,
                          IParameterHolder parameterHolder,
                          AbstractIteratorFactory iteratorFactory,
                          AbstractThreadFactory threadFactory,
                          AbstractTaskFactory runnableFactory,
                          AbstractStatisticFactory statisticFactory) {
        this.parameterHolder = parameterHolder;
        this.file = new File(fileDest);
        this.iterator = iteratorFactory.createIterator(file.length(), partSize);
        this.runnableFactory = runnableFactory;
        this.statisticService = statisticFactory.createService(file.length(), null);
        final SynchronousQueue<Runnable> workerQueue = new SynchronousQueue<>();
        int threadsCount = Integer.parseInt(parameterHolder.getValue("threadsCount"));
        threadFactory.setThreadName(parameterHolder.getValue("threadName"));
        pool = new ThreadPoolExecutor(threadsCount, threadsCount, 0L, TimeUnit.MILLISECONDS, workerQueue, threadFactory, new RejectedExecutionHandler() {
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
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     *
     */
    @Override
    public void process() {
        statisticService.show(0, 1000);
        for (int i = 0; i < pool.getCorePoolSize(); i++) {
            pool.execute(runnableFactory.createSplitTask(file, parameterHolder, iterator, statisticService));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.warn("Catches InterruptedException, during main thread waiting for pool. Message " + e.getMessage());
            throw new ApplicationException("Error during main thread sleeping. Exception:" + e.getMessage());
        }
        statisticService.hide();
    }
}
