package net.bondar.utils;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.Iterable;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.List;

/**
 *
 */
public class SplitMergeIteratorFactory implements AbstractIteratorFactory{


    @Override
    public Iterable createIterator(int fileLength, int partLength) {
        return new SplitIterator(fileLength, partLength);
    }
    public Iterable createIterator(List<File> parts){
        return new MergeIterator(parts);
    }
}
