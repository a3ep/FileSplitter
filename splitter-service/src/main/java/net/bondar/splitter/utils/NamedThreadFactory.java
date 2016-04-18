package net.bondar.splitter.utils;

import net.bondar.splitter.interfaces.AbstractThreadFactory;

/**
 * Creates named threads.
 */
public class NamedThreadFactory extends AbstractThreadFactory {

    /**
     * Thread name counter.
     */
    private int counter = 1;

    /**
     * Thread name prefix.
     */
    private String prefix;

    /**
     * Creates new <code>Thread</code> with custom name.
     *
     * @param r all heirs of <code>Runnable</code>
     * @return <code>Thread</code> instance
     * @see {@link AbstractThreadFactory}
     */
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + "-" + counter++);
    }

    /**
     * Sets name to <code>Thread</code>.
     *
     * @param name name of the thread
     * @see {@link AbstractThreadFactory}
     */
    @Override
    public void setThreadName(String name) {
        prefix = name;
    }
}
