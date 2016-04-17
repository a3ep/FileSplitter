package net.bondar;

import net.bondar.interfaces.*;

import java.io.File;
import java.util.List;

/**
 *
 */
public class FileStatisticFactory extends AbstractStatisticFactory {

    /**
     *
     * @param fileSize
     * @param files
     * @return
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
