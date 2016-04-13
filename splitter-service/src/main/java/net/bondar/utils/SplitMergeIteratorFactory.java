package net.bondar.utils;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.Iterable;

import java.io.BufferedInputStream;

/**
 *
 */
public class SplitMergeIteratorFactory implements AbstractIteratorFactory{


    @Override
    public Iterable createIterator(int fileLength, int partLength) {
        return new SplitIterator(fileLength, partLength);
    }
    public ReadFileIterator createIterator(BufferedInputStream bis){
        return new ReadFileIterator(bis);
    }
}
