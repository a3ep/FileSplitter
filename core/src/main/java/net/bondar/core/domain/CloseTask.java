package net.bondar.core.domain;

import net.bondar.calculations.FileCalculationUtils;
import net.bondar.core.interfaces.ICloseTask;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides cleaning temporary resources and closing application.
 */
public class CloseTask implements ICloseTask {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Interrupt flag.
     */
    private AtomicBoolean interrupt;

    /**
     * File processor.
     */
    private IProcessor processor;

    /**
     * Parameter holder.
     */
    private IConfigHolder parameterHolder;

    /**
     * Creates <code>CloseTask</code> instance.
     *
     * @param interrupt       flag for interrupting working threads
     * @param processor       file processor
     * @param parameterHolder parameter holder
     */
    public CloseTask(AtomicBoolean interrupt, IProcessor processor, IConfigHolder parameterHolder) {
        this.interrupt = interrupt;
        this.processor = processor;
        this.parameterHolder = parameterHolder;
    }

    /**
     * Cleans temporary files and closing application.
     */
    @Override
    public void run() {
        if (processor.getProcessStatus().equals("OK")) {
            return;
        }
        log.debug("Interrupting threads...");
        interrupt.set(true);
        log.debug("Cleaning temporary files...");
        if (processor.getCommandName().equalsIgnoreCase("split")) {
            FileCalculationUtils.getPartsList(processor.getFile().getAbsolutePath(), parameterHolder.getValue("partSuffix")).forEach(File::delete);
        } else {
            processor.getFile().delete();
        }
        log.info("Application closed.");
    }
}
