package net.bondar.utils;

import net.bondar.domain.Task;
import net.bondar.interfaces.Iterable;
import org.apache.log4j.Logger;

/**
 *
 */
public class SplitIterator implements Iterable{
    private final Logger log = Logger.getLogger("splitterLogger");
    private int partCounter = 1;
    private long currentPosition = 0;
    private long fileLength;
    private long partLength;

    public SplitIterator(int fileLength, int partLength) {
        this.fileLength = fileLength;
        this.partLength = partLength;
    }

    public synchronized Task getNext() {
        if(currentPosition>fileLength) return new Task();
        long start = currentPosition;
        long end = currentPosition + partLength;
        if (end > fileLength) end = fileLength;
        String partName = "_part_" + String.format("%03d", partCounter++);
        Task task = new Task(partName, start, end, partCounter-1);
        currentPosition=end+1;
        return task;
    }

}
