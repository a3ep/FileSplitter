package net.bondar.statistics.utils.simple;

import net.bondar.statistics.formatters.DelimiterFormat;
import net.bondar.statistics.formatters.ProgressFormat;
import net.bondar.statistics.formatters.TimerFormat;
import net.bondar.statistics.interfaces.ICalculatedDataObject;
import net.bondar.statistics.interfaces.IStatisticsFormatter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides formatting of simple statistical data.
 */
public class SimpleStatisticsFormatter implements IStatisticsFormatter {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Description for total progress value.
     */
    private final String totalProgressDescription;

    /**
     * Description for timer value.
     */
    private final String timerDescription;

    /**
     * Delimiter between parts of statistical information.
     */
    private final DelimiterFormat outerDelimiter;

    /**
     * Delimiter between description and value.
     */
    private final DelimiterFormat innerDelimiter;

    /**
     * Format for progress.
     */
    private final ProgressFormat progressFormat;

    /**
     * Format for timer.
     */
    private final TimerFormat timerFormat;

    /**
     * Creates <code>SimpleStatisticsFormatter</code> instance.
     *
     * @param totalProgressDescription description for total progress value
     * @param timerDescription         description for timer value
     * @param outerDelimiter           delimiter between parts of statistical information
     * @param innerDelimiter           delimiter between description and value
     * @param progressFormat           format for progress
     * @param timerFormat              format for timer
     */
    public SimpleStatisticsFormatter(String totalProgressDescription,
                                     String timerDescription,
                                     DelimiterFormat outerDelimiter,
                                     DelimiterFormat innerDelimiter,
                                     ProgressFormat progressFormat,
                                     TimerFormat timerFormat) {
        this.totalProgressDescription = totalProgressDescription;
        this.timerDescription = timerDescription;
        this.outerDelimiter = outerDelimiter;
        this.innerDelimiter = innerDelimiter;
        this.progressFormat = progressFormat;
        this.timerFormat = timerFormat;
    }

    /**
     * Formats statistical data.
     *
     * @param dataObject object with calculated statistical data
     * @return formatted string with statistical information
     */
    @Override
    public String format(final ICalculatedDataObject dataObject) {
        log.debug("Start formatting statistical data: " + dataObject.toString());
        String result = totalProgressDescription + " " + innerDelimiter.getValue() + " "
                + progressFormat.format(dataObject.getTotalProgress()) + outerDelimiter.getValue() + " "
                + timerDescription + " " + innerDelimiter.getValue() + " "
                + timerFormat.format(dataObject.getTimeRemaining());
        log.debug("Finish formatting statistical data. Formatted string: " + result);
        return result;
    }
}
