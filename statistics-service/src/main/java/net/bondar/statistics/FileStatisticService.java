package net.bondar.statistics;

import net.bondar.statistics.interfaces.*;

import java.util.Timer;

/**
 *
 */
public class FileStatisticService implements IStatisticService {

    private IStatisticHolder holder;

    private IStatisticBuilder builder;

    private AbstractStatisticTimerTask timerTask;

    private Timer timer = new Timer();

    public FileStatisticService(IStatisticHolder holder, IStatisticBuilder builder, AbstractStatisticTimerTask timerTask) {
        this.holder = holder;
        this.builder = builder;
        this.timerTask = timerTask;
    }

    public void holdInformation(String threadName, IPartObject filePart){
        holder.putInformation(threadName, filePart);
    }

    public String buildStatisticString(){
        return builder.buildStatisticString();
    }

    public void show(int delay, int period){
        timer.schedule(timerTask, delay, period);
    }

    public void hide(){
        timer.cancel();
    }
}
