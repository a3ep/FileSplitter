package net.bondar.core.domain;

import net.bondar.calculations.FileCalculationUtils;
import net.bondar.core.interfaces.ICloseTask;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.statistics.interfaces.IStatisticService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides cleaning temporary resources and closing application.
 */
public class CleanCloseTask implements ICloseTask {

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
     * Statistic service.
     */
    private IStatisticService statisticService;

    /**
     * Creates <code>CleanCloseTask</code> instance.
     *
     * @param interrupt        interrupt flag
     * @param processor        file processor
     * @param parameterHolder  parameter holder
     * @param statisticService statistic service
     */
    public CleanCloseTask(AtomicBoolean interrupt, IProcessor processor, IConfigHolder parameterHolder, IStatisticService statisticService) {
        this.interrupt = interrupt;
        this.processor = processor;
        this.parameterHolder = parameterHolder;
        this.statisticService = statisticService;
    }

    /**
     * Cleans temporary files and closing application.
     */
    @Override
    public void run() {
        if (processor.getProcessStatus().equals("OK")) {
            return;
        }
        statisticService.hide();
        log.debug("Interrupting threads...");
        interrupt.set(true);
        log.debug("Cleaning temporary files...");
        if (processor.getProcessOperation().equals("split")) {
            FileCalculationUtils.getPartsList(processor.getFile().getAbsolutePath(), parameterHolder.getValue("partSuffix")).forEach(File::delete);
        } else {
            processor.getFile().delete();
        }
        log.info("Application closed.");
    }
}
