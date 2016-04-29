package net.bondar.statistics.utils.simple;

import net.bondar.statistics.domain.DelimiterFormat;
import net.bondar.statistics.domain.ProgressFormat;
import net.bondar.statistics.domain.TimerFormat;
import net.bondar.statistics.interfaces.ISimpleStatisticsCalculator;
import net.bondar.statistics.interfaces.ISimpleStatisticsFormatter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Provides formatting of simple statistical data.
 */
public class SimpleStatisticsFormatter implements ISimpleStatisticsFormatter {

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
    private final ISimpleStatisticsCalculator calculator;

    /**
     * Creates <code>AdvancedStatisticsFormatter</code> instance.
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
                                     ISimpleStatisticsCalculator calculator) {
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
