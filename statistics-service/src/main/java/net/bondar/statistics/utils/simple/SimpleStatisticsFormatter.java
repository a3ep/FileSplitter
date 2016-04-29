package net.bondar.statistics.utils.simple;

import net.bondar.statistics.formatters.DelimiterFormat;
import net.bondar.statistics.formatters.ProgressFormat;
import net.bondar.statistics.formatters.TimerFormat;
import net.bondar.statistics.interfaces.IStatisticsCalculator;
import net.bondar.statistics.interfaces.IStatisticsFormatter;

import java.util.List;

/**
 * Provides formatting of simple statistical data.
 */
public class SimpleStatisticsFormatter implements IStatisticsFormatter {

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
     * Statistics calculator.
     */
    private final IStatisticsCalculator calculator;

    /**
     * Creates <code>SimpleStatisticsFormatter</code> instance.
     *
     * @param totalProgressDescription description for total progress value
     * @param timerDescription         description for timer value
     * @param outerDelimiter           delimiter between parts of statistical information
     * @param innerDelimiter           delimiter between description and value
     * @param progressFormat           format for progress
     * @param timerFormat              format for timer
     * @param calculator               statistics calculator
     */
    public SimpleStatisticsFormatter(String totalProgressDescription,
                                     String timerDescription,
                                     DelimiterFormat outerDelimiter,
                                     DelimiterFormat innerDelimiter,
                                     ProgressFormat progressFormat,
                                     TimerFormat timerFormat,
                                     IStatisticsCalculator calculator) {
        this.totalProgressDescription = totalProgressDescription;
        this.timerDescription = timerDescription;
        this.outerDelimiter = outerDelimiter;
        this.innerDelimiter = innerDelimiter;
        this.progressFormat = progressFormat;
        this.timerFormat = timerFormat;
        this.calculator = calculator;
    }

    @Override
    public String format() {
        List<Double> data = calculator.calculate();
        return totalProgressDescription + " " + innerDelimiter.getValue() + " " + progressFormat.format(data.remove(0))
                + outerDelimiter.getValue() + " " + timerDescription + " " + innerDelimiter.getValue() + " "
                + timerFormat.format(data.get(data.size() - 1));
    }
}
