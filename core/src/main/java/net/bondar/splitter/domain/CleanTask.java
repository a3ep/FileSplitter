package net.bondar.splitter.domain;

import net.bondar.calculations.Calculations;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.interfaces.ITask;
import net.bondar.statistics.interfaces.IStatisticService;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides clean tasks for thread pool.
 */
public class CleanTask implements ITask {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Specified file.
     */
    private final File file;

    /**
     * Parameter holder
     */
    private final IParameterHolder paramHolder;

    /**
     * Statistic service.
     */
    private final IStatisticService statService;

    /**
     * Interrupt flag.
     */
    private AtomicBoolean interrupt;

    /**
     * Processor operation.
     */
    private final String operation;

    /**
     * Initialises <code>CleanTask</code> fields.
     *
     * @param file        specified file
     * @param interrupt   interrupt flag
     * @param operation   processor operation
     * @param paramHolder parameter holder
     * @param statService statistic service
     */
    public CleanTask(File file, AtomicBoolean interrupt, String operation, IParameterHolder paramHolder, IStatisticService statService) {
        this.file = file;
        this.interrupt = interrupt;
        this.operation = operation;
        this.paramHolder = paramHolder;
        this.statService = statService;
    }

    /**
     * Runs interrupting working threads and cleaning temporary files.
     */
    @Override
    public void run() {
        statService.hide();
        log.debug("Interrupting threads...");
        interrupt.set(true);
        log.debug("Cleaning temporary files...");
        if (operation.equals("split")) {
            Calculations.getPartsList(file.getAbsolutePath(), paramHolder.getValue("partSuffix")).forEach(File::delete);
        } else {
            file.delete();
        }
        log.info("Application closed.");
    }
}
