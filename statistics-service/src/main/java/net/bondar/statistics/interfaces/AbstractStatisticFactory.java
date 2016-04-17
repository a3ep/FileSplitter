package net.bondar.statistics.interfaces;

import java.io.File;
import java.util.List;

/**
 *
 */
public abstract class AbstractStatisticFactory {

    /**
     *
     * @param fileSize
     * @param files
     * @return
     */
    public abstract IStatisticService createService(double fileSize, List<File> files);
}
