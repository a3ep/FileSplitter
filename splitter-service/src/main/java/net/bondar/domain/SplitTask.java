package net.bondar.domain;

import net.bondar.interfaces.AbstractTask;
import net.bondar.interfaces.IParameterHolder;
import net.bondar.interfaces.IStatisticService;
import net.bondar.interfaces.Iterable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 */
public class SplitTask extends AbstractTask {

    /**
     * @param file
     * @param parameterHolder
     * @param iterator
     * @param statisticService
     */
    public SplitTask(File file, IParameterHolder parameterHolder, Iterable iterator, IStatisticService statisticService) {
        super(file, parameterHolder, iterator, statisticService);
    }

    /**
     *
     */
    @Override
    public void run() {
        filePart = iterator.getNext();
        while (!filePart.getStatus().equals("NULL")) {
            File partFile = new File(file.getParent(), file.getName() + filePart.getPartFileName());
            try (RandomAccessFile sourceFile = new RandomAccessFile(file, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(partFile, "rw")) {
                log.info("Start to write: " + partFile.getName());
                // /Set the file-pointer to the start position of partFile
                sourceFile.seek(filePart.getStartPosition());
                start = filePart.getStartPosition();
                long finish = filePart.getEndPosition();
                int bufferSize = Integer.parseInt(parameterHolder.getValue("bufferSize"));
                // write data into file
                readWrite(sourceFile, outputFile, finish, bufferSize);
                log.info("Finish to write: " + partFile.getName());
                filePart = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + partFile + ". Message " + e.getMessage());
                return;
            }
        }
    }
}
