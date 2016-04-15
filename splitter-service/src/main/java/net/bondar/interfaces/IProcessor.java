package net.bondar.interfaces;

import java.io.File;
import java.util.List;

/**
 *
 */
public interface IProcessor {

    /**
     *
     */
    void process();

    File getFile();

    List<File> getFiles();
}
