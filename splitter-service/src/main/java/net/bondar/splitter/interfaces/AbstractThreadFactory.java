package net.bondar.splitter.interfaces;

import java.util.concurrent.ThreadFactory;

/**
 * Interface for creating concrete threads.
 */
public abstract class AbstractThreadFactory implements ThreadFactory {

    /**
     * Creates new <code>Thread</code>.
     *
     * @param r all heirs of <code>Runnable</code>
     * @return <code>Thread</code> instance
     */
    public abstract Thread newThread(Runnable r);

    /**
     * Sets name to <code>Thread</code>.
     *
     * @param name name of thread
     */
    public abstract void setThreadName(String name);
}
