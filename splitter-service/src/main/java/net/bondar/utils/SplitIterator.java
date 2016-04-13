package net.bondar.utils;

import net.bondar.domain.Task;
import net.bondar.interfaces.Iterable;

/**
 *
 */
public class SplitIterator implements Iterable{
    private int partCounter = 1;
    private int currentPosition = 0;
    private int fileLength;
    private int partLength;

    public SplitIterator(int fileLength, int partLength) {
        this.fileLength = fileLength;
        this.partLength = partLength;
    }

    public synchronized Task getNext() {
        if(currentPosition==fileLength){
            return new Task();
        }
        int start = currentPosition;
        int end = currentPosition + partLength;
        if (end > fileLength) end = fileLength;
        String partName = "_part_" + String.format("%03d", partCounter++);
        Task task = new Task(partName, start, end);
        currentPosition=end;
        return task;
    }

}
