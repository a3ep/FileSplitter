package net.bondar.utils;

import net.bondar.domain.Task;
import net.bondar.interfaces.Iterable;

import java.io.File;
import java.util.List;

/**
 *
 */
public class MergeIterator implements Iterable {

    /**
     *
     */
    private int counter = 1;

    /**
     *
     */
    private long currentPosition = 0;

    /**
     *
     */
    private List<File> parts;

    /**
     * @param parts
     */
    public MergeIterator(List<File> parts) {
        this.parts = parts;
    }

    /**
     * @return
     */
    @Override
    public synchronized Task getNext() {
        if (parts.isEmpty()) {
            return new Task();
        }
        long start = currentPosition;
        File part = parts.remove(0);
        Task task = new Task(part, start, counter++);
        currentPosition += (int) part.length() + 1;
        return task;
    }
}
