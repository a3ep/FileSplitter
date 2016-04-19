package net.bondar.splitter.domain;

import net.bondar.calculations.Calculations;
import net.bondar.splitter.interfaces.AbstractTask;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.interfaces.IProcessor;
import net.bondar.statistics.interfaces.IStatisticService;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Provides clean tasks for thread pool.
 */
public class CleanTask extends AbstractTask {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Creates <code>CleanTask</code> instance.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param statService statistic service
     * @see {@link AbstractTask}
     */
    public CleanTask(File file, IProcessor processor, IParameterHolder paramHolder, IStatisticService statService) {
        super(file, processor, paramHolder, null, statService);
    }

    /**
     * Runs interrupting working threads and cleaning temporary files.
     */
    @Override
    public void run() {
        if (processor.getProcessStatus().equals("OK")) {
            return;
        }
        statService.hide();
        log.debug("Interruption threads work...");
        processor.setInterrupt(true);
        log.debug("Cleaning temporary files...");
        if (processor.getFileOperation().equals("split")) {
            for (File f : Calculations.getPartsList(file.getAbsolutePath(), paramHolder.getValue("partSuffix")))
                f.delete();
        } else if (processor.getFileOperation().equals("merge")) {
            file.delete();
        }
    }
}
