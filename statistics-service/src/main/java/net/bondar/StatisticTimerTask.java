package net.bondar;

import java.util.TimerTask;

/**
 *
 */
public class StatisticTimerTask extends TimerTask{

    /**
     *
     */
    private StatisticViewer statisticViewer;

    /**
     *
     * @param viewer
     */
    public StatisticTimerTask(StatisticViewer viewer) {
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
