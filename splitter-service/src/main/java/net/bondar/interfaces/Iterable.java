package net.bondar.interfaces;

/**
 *
 */
public interface Iterable {

    boolean hasNext();

    int next();

    byte[] getBuffer();

    int getTmpNumberOfData();
}
