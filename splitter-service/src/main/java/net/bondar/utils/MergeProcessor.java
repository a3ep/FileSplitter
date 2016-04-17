package net.bondar.utils;

import net.bondar.domain.FilePart;
import net.bondar.exceptions.ApplicationException;
import net.bondar.interfaces.*;
import net.bondar.interfaces.Iterable;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class MergeProcessor implements IProcessor {

    /**
     *
     */
    private final Logger log = Logger.getLogger("splitterLogger");

    /**
     *
     */
    private final String partFileDest;

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
    private AbstractTaskFactory runnableFactory;

    /**
     *
     */
    private IStatisticService statisticService;

    /**
     *
     * @param partFileDest
     * @param parameterHolder
     * @param iteratorFactory
     * @param threadFactory
     * @param runnableFactory
     * @param statisticFactory
     */
    public  MergeProcessor(String partFileDest,
                           IParameterHolder parameterHolder,
                           AbstractIteratorFactory iteratorFactory,
                           AbstractThreadFactory threadFactory,
                           AbstractTaskFactory runnableFactory,
                           AbstractStatisticFactory statisticFactory){
        this.parameterHolder = parameterHolder;
        this.partFileDest = partFileDest;
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf("_")));
        this.iterator = iteratorFactory.createIterator(Calculations.getPartsList(partFileDest));
        this.runnableFactory = runnableFactory;
        this.statisticService = statisticFactory.createService(0, Calculations.getPartsList(partFileDest));
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
     *
     * @return
     */
    @Override
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
            pool.execute(runnableFactory.createMergeTask(file, parameterHolder, iterator, statisticService));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.warn("Catches InterruptedException, during main thread waiting for pool. Message " + e.getMessage());
            statisticService.hide();
            throw new ApplicationException("Error during main thread sleeping. Exception:" + e.getMessage());
        }
        statisticService.hide();
    }
}
