package net.bondar.statistics;

import net.bondar.statistics.interfaces.AbstractStatisticTimerTask;
import net.bondar.statistics.interfaces.IStatisticViewer;

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
