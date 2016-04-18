package net.bondar.statistics;

import net.bondar.statistics.interfaces.*;

import java.io.File;
import java.util.List;

/**
 * Provides creating of concrete statistic services.
 */
public class FileStatisticFactory extends AbstractStatisticFactory {

    /**
     * Creates <code>FileStatisticService</code>.
     *
     * @param fileSize size of specified file
     * @param files    list of part-files
     * @return <code>FileStatisticService</code> instance
     */
    @Override
    public IStatisticService createService(double fileSize, List<File> files) {
        IStatisticHolder holder = new FileStatisticHolder();
        IStatisticBuilder builder;
        if (files == null) {
            builder = new FileStatisticBuilder(fileSize, holder);
        } else {
            builder = new FileStatisticBuilder(files, holder);
        }
        IStatisticViewer viewer = new FileStatisticViewer(builder);
        AbstractStatisticTimerTask timerTask = new FileStatisticTimerTask(viewer);
        return new FileStatisticService(holder, builder, timerTask);
    }
}
