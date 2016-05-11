package net.bondar.core.tasks;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.core.interfaces.tasks.ICloseTask;
import net.bondar.core.utils.Command;
import net.bondar.core.utils.FilesFinder;
import net.bondar.core.utils.ProcessorStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
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
    private IProcessor processor;

    /**
     * Configuration holder.
     */
    private IConfigHolder configHolder;

    /**
     * Creates <code>CloseTask</code> instance.
     *
     * @param processor    file splitter processor
     * @param configHolder configuration holder
     * @param futures      list of futures that represented threads in a pool
     */
    public CloseTask(IProcessor processor, IConfigHolder configHolder, final List<Future> futures) {
        this.processor = processor;
        this.configHolder = configHolder;
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
        log.debug("Start cleaning temporary files...");
        try {
            if (processor.getCommandName().equalsIgnoreCase(Command.SPLIT.name())) {
                List<File> files = FilesFinder.getPartsList(processor.getFile().getAbsolutePath(), configHolder.getValue("partSuffix"));
                log.debug("Deleting temporary files.");
                files.forEach(File::delete);
            } else {
                File file = processor.getFile();
                log.debug("Deleting temporary file: " + file.getAbsolutePath());
                file.delete();
            }
        } catch (SecurityException e) {
            log.warn("Error while cleaning temporary files. Message: " + e.getMessage());
        }
        log.debug("Finish cleaning temporary files.");
        log.info("Application closed.");
    }
}
