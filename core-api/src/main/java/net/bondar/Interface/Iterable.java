package net.bondar.Interface;

/**
 *
 */
public interface Iterable {

    boolean hasNext();

    int next();

    byte[] getBuffer();

    int getTmpNumberOfData();
}
