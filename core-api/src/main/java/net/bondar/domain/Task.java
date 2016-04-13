package net.bondar.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 */
public class Task implements Runnable {
    private int partCounter;
    private int tmpNumberOfDate;
    private byte[] buffer;
    private File file;

    public Task(int partCounter, int tmpNumberOfDate, byte[] buffer, File file) {
        this.partCounter = partCounter;
        this.tmpNumberOfDate = tmpNumberOfDate;
        this.buffer = buffer;
        this.file = file;
    }

    public int getPartCounter() {
        return partCounter;
    }

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " processes Task " + partCounter);
        File partFile = new File(file.getParent(), file.getName() + "_part_" + String.format("%03d", partCounter));
        try (FileOutputStream fos = new FileOutputStream(partFile)) {
            fos.write(buffer, 0, tmpNumberOfDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "partCounter=" + partCounter +
                ", tmpNumberOfDate=" + tmpNumberOfDate +
                ", buffer=" + Arrays.toString(buffer) +
                ", file=" + file +
                '}';
    }
}
