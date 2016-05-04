package net.bondar.core.utils.factories;

import java.util.concurrent.ThreadFactory;

/**
 * Creates named threads.
 */
public class NamedThreadFactory implements ThreadFactory {

    /**
     * Thread name.
     */
    private static final String THREAD_NAME = "Worker";

    /**
     * Thread counter.
     */
    private int counter = 1;

    /**
     * Creates named thread.
     *
     * @param r thread task <code>Runnable</code>
     * @return named thread
     */
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, THREAD_NAME + "-" + counter++);
    }
}
