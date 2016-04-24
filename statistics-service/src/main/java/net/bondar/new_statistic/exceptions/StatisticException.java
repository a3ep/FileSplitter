package net.bondar.new_statistic.exceptions;

/**
 * Custom statistic exception.
 */
public class StatisticException extends RuntimeException{

    /**
     * Creates <code>ParsingException</code> instance.
     *
     * @param message the detail error message
     */
    public StatisticException(String message) {
        super(message);
    }

    /**
     * Creates <code>ParsingException</code> instance.
     *
     * @param message the detail error message
     * @param cause the cause
     */
    public StatisticException(String message, Throwable cause) {
        super(message, cause);
    }
}
