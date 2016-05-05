package net.bondar.core.tasks;

import net.bondar.calculations.FileCalculationUtils;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.core.interfaces.tasks.ICloseTask;
import net.bondar.core.utils.Command;
import net.bondar.core.utils.ProcessorStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Provides cleaning temporary resources and closing application.
 */
public class CloseTask implements ICloseTask {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Name of the threads that need to interrupt.
     */
    private final String threadName;

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
     * @param threadName   name of the threads that need to interrupt
     */
    public CloseTask(IProcessor processor, IConfigHolder configHolder, final String threadName) {
        this.processor = processor;
        this.configHolder = configHolder;
        this.threadName = threadName;
    }

    /**
     * Cleans temporary files and closing application.
     */
    @Override
    public void run() {
        if (processor.getProcessorStatus().equals(ProcessorStatus.DONE)) {
            return;
        }
        log.debug("Interrupting threads...");
        Thread.getAllStackTraces().keySet().stream().filter(thread -> thread.getName().contains(threadName)).forEach(Thread::interrupt);
        log.debug("Cleaning temporary files...");
        if (processor.getCommandName().equalsIgnoreCase(Command.SPLIT.name())) {
            FileCalculationUtils.getPartsList(processor.getFile().getAbsolutePath(), configHolder.getValue("partSuffix")).forEach(File::delete);
        } else {
            processor.getFile().delete();
        }
        log.info("Application closed.");
    }
}
