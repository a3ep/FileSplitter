package net.bondar.utils;

import net.bondar.domain.Task;
import net.bondar.interfaces.Iterable;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 *
 */
public class ReadFileIterator implements Iterable{

    private int tmpNumberOfData;
    /**
     *
     */
    private byte[] buffer = null;

    /**
     *
     */
    private BufferedInputStream bis;

    /**
     * @param bis
     * @param buffer
     */
    public ReadFileIterator(BufferedInputStream bis, byte[] buffer) {
        this.bis = bis;
        this.buffer = buffer;
    }

    public ReadFileIterator(BufferedInputStream bis) {
        this.bis = bis;
    }

    public int getTmpNumberOfData() {
        return tmpNumberOfData;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * @return
     */
    public boolean hasNext() {
        try {
            if (buffer == null) {
                return (tmpNumberOfData = bis.read()) != -1;
            } else {
                return (tmpNumberOfData = bis.read(buffer))!=-1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return
     */
    public int next() {
        return tmpNumberOfData;
    }

    @Override
    public Task getNext() {
        return null;
    }
}
