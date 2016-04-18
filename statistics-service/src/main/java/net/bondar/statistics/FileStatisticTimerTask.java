package net.bondar.statistics;

import net.bondar.statistics.interfaces.AbstractStatisticTimerTask;
import net.bondar.statistics.interfaces.IStatisticViewer;

/**
 * Provides creating statistic timer tasks for files.
 */
class FileStatisticTimerTask extends AbstractStatisticTimerTask {

    /**
     * Statistic viewer.
     */
    private IStatisticViewer viewer;

    /**
     * Creates <code>FileStatisticTimerTask</code> instance.
     *
     * @param viewer statistic viewer
     * @see {@link AbstractStatisticTimerTask}
     */
    FileStatisticTimerTask(IStatisticViewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Runs task for showing statistic information.
     *
     * @see {@link AbstractStatisticTimerTask}
     */
    @Override
    public void run() {
        viewer.showStatistic();
    }
}
