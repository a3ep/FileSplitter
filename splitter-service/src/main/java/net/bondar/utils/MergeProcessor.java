package net.bondar.utils;

import net.bondar.domain.Task;
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
    private AbstractRunnableFactory runnableFactory;

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
                           AbstractRunnableFactory runnableFactory,
                           AbstractStatisticFactory statisticFactory){
        this.parameterHolder = parameterHolder;
        this.partFileDest = partFileDest;
        this.file = new File(partFileDest.substring(0, partFileDest.indexOf("_")));
        this.iterator = iteratorFactory.createIterator(getPartsList(partFileDest));
        this.runnableFactory = runnableFactory;
        this.statisticService = statisticFactory.createService(0, getPartsList(partFileDest));
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
//     * @param partFileDest
//     * @param iteratorFactory
//     */
//    public MergeProcessor(String partFileDest, AbstractIteratorFactory iteratorFactory) {
//        IParameterHolder configLoader = new ApplicationParameterHolder();
//        this.partFileDest = partFileDest;
//        this.file = new File(partFileDest.substring(0, partFileDest.indexOf("_")));
//        List<File> parts = getPartsList(partFileDest);
//        this.iterator = iteratorFactory.createIterator(parts);
//        IStatisticHolder statisticHolder = new FileStatisticHolder();
//        IStatisticBuilder statisticBuilder = new FileStatisticBuilder(parts, statisticHolder);
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
            pool.execute(runnableFactory.createRunnable(this::processTask));
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

    /**
     *
     */
    public void processTask() {
        Task task = iterator.getNext();
        while (!task.getStatus().equals("NULL")) {
            log.info("Thread " + Thread.currentThread().getName() + " processed " + task.getName() + task.getCounter());
            File part = task.getFile();
            try (RandomAccessFile sourceFile = new RandomAccessFile(part, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(file, "rw")) {
                log.info("Start to write " + part.getName() + " into Complete file : " + file.getName());
                long start = task.getStartPosition();
                long finish = start+part.length();
                int bufferSize = Integer.parseInt(parameterHolder.getValue("bufferSize"));
                //Set the file-pointer to the start position of partFile
                outputFile.seek(start);
                while (start < finish) {
                    byte[] array = new byte[getAvaliableSize(finish, start, bufferSize)];
                    //process of copying
                    sourceFile.read(array);
                    outputFile.write(array);
                    start+=array.length;
                    statisticService.holdInformation(Thread.currentThread().getName(), start-task.getStartPosition());
                }
                log.info("Finish to write " + part.getName() + " into " + file.getName());
                task = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + part.getName() + " into " + file.getName() + ". Message " + e.getMessage());
                return;
            }
        }
    }

    /**
     * @param dest
     * @return
     */
    private List<File> getPartsList(String dest) {
        File partFile = new File(dest);
        String partName = partFile.getName();
        String destName = partName.substring(0, partName.indexOf("_part_"));
        File file = partFile.getParentFile();
        File[] files = file.listFiles((File dir, String name) -> name.matches(destName + ".+\\_\\d+"));
        Arrays.sort(files);
        List<File> parts = new LinkedList<>();
        Collections.addAll(parts, files);
        return parts;
    }

    /**
     *
     * @param finish
     * @param start
     * @param buffer
     * @return
     */
    private int getAvaliableSize(long finish, long start, int buffer){
        if(finish-start>buffer) return buffer;
        else return (int) (finish-start);

    }
}
