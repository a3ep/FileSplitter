package net.bondar.statistics.interfaces;

import java.io.File;
import java.util.List;

/**
 * Interface for creating statistic services.
 */
public abstract class AbstractStatisticFactory {

    /**
     * Creates statistic service.
     *
     * @param fileSize size of specified file
     * @param files    list of part-files
     * @return <code>IStatisticService</code> instance
     */
    public abstract IStatisticService createService(double fileSize, List<File> files);
}
