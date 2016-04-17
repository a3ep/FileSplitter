package net.bondar.utils;

import net.bondar.domain.Task;
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
    private final AbstractRunnableFactory runnableFactory;
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
                          AbstractRunnableFactory runnableFactory,
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

//    /**
//     * @param fileDest
//     * @param partSize
//     * @param iteratorFactory
//     */
//    public SplitProcessor(String fileDest, int partSize, AbstractIteratorFactory iteratorFactory) {
//        IParameterHolder configLoader = new ApplicationParameterHolder();
//        this.file = new File(fileDest);
//        this.iterator = iteratorFactory.createIterator((int) file.length(), partSize);
//        IStatisticHolder statisticHolder = new FileStatisticHolder();
//        IStatisticBuilder statisticBuilder = new FileStatisticBuilder(file.length(), statisticHolder);
//        IStatisticViewer statisticViewer = new FileStatisticViewer(statisticBuilder);
//        this.statisticService = new FileStatisticService(statisticHolder, statisticBuilder, new FileStatisticTimerTask(statisticViewer));
//        final SynchronousQueue<Runnable> workerQueue = new SynchronousQueue<>();
//        pool = new ThreadPoolExecutor(Integer.parseInt(configLoader.getValue("threadsCount")),
//                Integer.parseInt(configLoader.getValue("threadsCount")),
//                0L,
//                TimeUnit.MILLISECONDS,
//                workerQueue,
//                new NamedThreadFactory(configLoader.getValue("threadName")),
//                new RejectedExecutionHandler() {
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor pool) {
//                        try {
//                            pool.getQueue().put(r);
//                        } catch (InterruptedException e) {
//                            log.warn("Catches InterruptedException, during putting tasks into queue. Message " + e.getMessage());
//                            throw new ApplicationException("Error during putting tasks into queue. Exception:" + e.getMessage());
//                        }
//                    }
//                });
//    }

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
            pool.execute(runnableFactory.createRunnable(this::processTask));
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

    /**
     *
     */
    public void processTask() {
        Task task = iterator.getNext();
        while (!task.getStatus().equals("NULL")) {
            log.info("Thread " + Thread.currentThread().getName() + " processed " + task.getName() + task.getCounter());
            File partFile = new File(file.getParent(), file.getName() + task.getPartFileName());
            try (RandomAccessFile sourceFile = new RandomAccessFile(file, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(partFile, "rw")) {
                log.info("Start to write: " + partFile.getName());
                // /Set the file-pointer to the start position of partFile
                sourceFile.seek(task.getStartPosition());
                long start = task.getStartPosition();
                long finish = task.getEndPosition();
                int bufferSize = Integer.parseInt(parameterHolder.getValue("bufferSize"));

                while (start < finish) {
                    //create buffer for copying
                    byte[] array = new byte[getAvaliableSize(finish, start, bufferSize)];
                    log.info("Avaliable size = " + array.length);
                    //process of copying
                    sourceFile.read(array);
                    outputFile.write(array);
                    start += array.length;
                    statisticService.holdInformation(Thread.currentThread().getName(), start - task.getStartPosition());
                }
                log.info("Finish to write: " + partFile.getName());
                task = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + partFile + ". Message " + e.getMessage());
                return;
            }
        }
    }

    /**
     * @param finish
     * @param start
     * @param buffer
     * @return
     */
    private int getAvaliableSize(long finish, long start, int buffer) {
        if (finish - start > buffer) return buffer;
        else return (int) (finish - start);

    }
}
