package net.bondar.core.tasks;

import net.bondar.core.interfaces.tasks.ICloseTask;
import net.bondar.core.utils.FileProcessor;
import net.bondar.core.utils.ProcessorStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Provides cleaning temporary resources and closing application.
 */
public class CloseTask implements ICloseTask {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * List of futures that represented threads in a pool.
     */
    private final List<Future> futures;

    /**
     * File splitter processor.
     */
    private FileProcessor processor;


    /**
     * Creates <code>CloseTask</code> instance.
     *
     * @param processor file splitter processor
     * @param futures   list of futures that represented threads in a pool
     */
    public CloseTask(FileProcessor processor, final List<Future> futures) {
        this.processor = processor;
        this.futures = futures;
    }

    /**
     * Cleans temporary files and closing application.
     */
    @Override
    public void run() {
        if (processor.getProcessorStatus().equals(ProcessorStatus.DONE)) {
            return;
        }
        log.debug("Start interrupting threads...");
        for (Future future : futures) {
            future.cancel(true);
        }
        log.debug("Finish interrupting threads.");
    }
}
