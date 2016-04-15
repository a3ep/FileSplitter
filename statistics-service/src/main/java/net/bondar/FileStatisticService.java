package net.bondar;

import net.bondar.interfaces.*;

import java.util.Timer;

/**
 *
 */
public class FileStatisticService implements IStatisticService{

    private IStatisticHolder holder;

    private IStatisticBuilder builder;

    private AbstractStatisticTimerTask timerTask;

    private Timer timer = new Timer();

    public FileStatisticService(IStatisticHolder holder, IStatisticBuilder builder, AbstractStatisticTimerTask timerTask) {
        this.holder = holder;
        this.builder = builder;
        this.timerTask = timerTask;
    }

    public void holdInformation(String threadName, long value){
        holder.putInformation(threadName, value);
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
