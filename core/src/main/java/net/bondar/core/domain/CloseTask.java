package net.bondar.core.domain;

import net.bondar.core.interfaces.ICloseTask;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides closing application.
 */
public class CloseTask implements ICloseTask {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     *
     */
    private AtomicBoolean interrupt;

    /**
     * Creates <code>CloseTask</code> instance.
     *
     * @param interrupt interrupt flag
     */
    public CloseTask(AtomicBoolean interrupt) {
        this.interrupt = interrupt;
    }

    /**
     * Closes working threads.
     */
    @Override
    public void run() {
        log.debug("Interrupting threads...");
        interrupt.set(true);
        log.info("Application closed.");
    }
}
