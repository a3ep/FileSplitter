package net.bondar.utils;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.IProcessor;
import net.bondar.interfaces.Iterable;

import java.io.*;

/**
 *
 */
public class SplitProcessor implements IProcessor{

    /**
     *
     */
    private String fileDest;

    /**
     *
     */
    private String chunkSize;
    /**
     *
     */
    private AbstractIteratorFactory iFactory;

    /**
     *
     * @param fileDest
     * @param chunkSize
     * @param iFactory
     */
    public SplitProcessor(String fileDest, String chunkSize, AbstractIteratorFactory iFactory) {
        this.fileDest = fileDest;
        this.chunkSize = chunkSize;
        this.iFactory = iFactory;
    }

    /**
     *
     */
    public void process() {
        int partCounter = 1;
        File file = new File(fileDest);
        int partSize = Integer.parseInt(chunkSize.substring(0, chunkSize.indexOf("M")).replace(" ", "")) * 1024 * 1024;
        byte[] buffer = new byte[partSize];

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            Iterable iterator = iFactory.createIterator(bis, buffer);
            while (iterator.hasNext()) {
                File partFile = new File(file.getParent(), file.getName() + "_part_" + String.format("%03d", partCounter++));
                try (FileOutputStream fos = new FileOutputStream(partFile)) {
                    fos.write(iterator.getBuffer(), 0, iterator.getTmpNumberOfData());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
//        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
//            ReadFileIterator iterator = new ReadFileIterator(bis, buffer);
//            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
//            List<Runnable> taskList = new ArrayList<>();
//            while (true) {
//                if (iterator.hasNext()) {
//                    byte[] tmpBuffer = iterator.getBuffer();
//                    int tmpNumberOfData = iterator.getTmpNumberOfData();
//                    Task task = new Task(partCounter++, tmpNumberOfData, tmpBuffer, file);
//                    taskList.add(task);
//                } else {
//                    break;
//                }
//            }
//            for (Runnable task : taskList) {
//                executor.execute(task);
//            }
//            executor.shutdown();
//            while (!executor.isTerminated()) {
//            }
//            System.out.println("Finished all threads");
//        }

    }
}
