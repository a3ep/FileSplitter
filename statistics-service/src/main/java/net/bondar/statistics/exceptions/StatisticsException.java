package net.bondar.statistics.exceptions;

/**
 * Custom statistic exception.
 */
public class StatisticsException extends RuntimeException {

    /**
     * Creates <code>ParsingException</code> instance.
     *
     * @param message the detail error message
     */
    public StatisticsException(String message) {
        super(message);
    }

    /**
     * Creates <code>ParsingException</code> instance.
     *
     * @param message the detail error message
     * @param cause   the cause
     */
    public StatisticsException(String message, Throwable cause) {
        super(message, cause);
    }
}
