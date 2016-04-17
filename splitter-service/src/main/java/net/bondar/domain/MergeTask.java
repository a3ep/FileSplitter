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
public class MergeTask extends AbstractTask {

    /**
     * @param file
     * @param parameterHolder
     * @param iterator
     * @param statisticService
     */
    public MergeTask(File file, IParameterHolder parameterHolder, Iterable iterator, IStatisticService statisticService) {
        super(file, parameterHolder, iterator, statisticService);

    }

    /**
     *
     */
    @Override
    public void run() {
        filePart = iterator.getNext();
        while (!filePart.getStatus().equals("NULL")) {
            File part = filePart.getFile();
            try (RandomAccessFile sourceFile = new RandomAccessFile(part, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(file, "rw")) {
                log.info("Start to write " + part.getName() + " into Complete file : " + file.getName());
                start = filePart.getStartPosition();
                long finish = filePart.getEndPosition();
                int bufferSize = Integer.parseInt(parameterHolder.getValue("bufferSize"));
                //Set the file-pointer to the start position of partFile
                outputFile.seek(start);
                // write data into file
                readWrite(sourceFile, outputFile, finish, bufferSize);
                log.info("Finish to write " + part.getName() + " into " + file.getName());
                filePart = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + part.getName() + " into " + file.getName() + ". Message " + e.getMessage());
                return;
            }
        }
    }
}
