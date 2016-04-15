package net.bondar;

import net.bondar.interfaces.AbstractStatisticTimerTask;
import net.bondar.interfaces.IStatisticViewer;

/**
 *
 */
public class FileStatisticTimerTask extends AbstractStatisticTimerTask {

    /**
     *
     */
    private IStatisticViewer statisticViewer;

    /**
     *
     * @param viewer
     */
    public FileStatisticTimerTask(IStatisticViewer viewer) {
        this.statisticViewer = viewer;
    }

    /**
     *
     */
    @Override
    public void run() {
        statisticViewer.showStatistic();
    }
}
