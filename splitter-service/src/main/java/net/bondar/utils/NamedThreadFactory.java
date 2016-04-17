package net.bondar.utils;

import net.bondar.interfaces.AbstractThreadFactory;

/**
 *
 */
public class NamedThreadFactory extends AbstractThreadFactory {

    /**
     *
     */
    private int counter = 1;

    /**
     *
     */
    private String prefix;


    /**
     * @param r
     * @return
     */
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + "-" + counter++);
    }

    /**
     *
     * @param name
     */
    @Override
    public void setThreadName(String name) {
        prefix=name;
    }
}
