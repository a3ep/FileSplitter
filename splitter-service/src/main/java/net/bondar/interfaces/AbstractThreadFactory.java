package net.bondar.interfaces;

import java.util.concurrent.ThreadFactory;

/**
 *
 */
public abstract class AbstractThreadFactory implements ThreadFactory {

    /**
     * @param r
     * @return
     */
    public abstract Thread newThread(Runnable r);

    public  abstract void setThreadName(String name);
}
