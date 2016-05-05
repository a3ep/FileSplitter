package net.bondar.calculations.exceptions;

/**
 * Custom application exception.
 */
public class CalculationsException extends RuntimeException {

    /**
     * Creates <code>CalculationsException</code> instance.
     */
    public CalculationsException() {
        super();
    }

    /**
     * Creates <code>CalculationsException</code> instance.
     *
     * @param message exception message
     */
    public CalculationsException(String message) {
        super(message);
    }

    /**
     * Creates <code>RunException</code> instance.
     *
     * @param message the detail error message
     * @param cause   the cause of exception
     */
    public CalculationsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates <code>CalculationsException</code> instance.
     *
     * @param cause the cause of exception
     */
    public CalculationsException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates <code>CalculationsException</code> instance.
     *
     * @param message exception message
     * @param cause the cause of exception
     * @param enableSuppression whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    protected CalculationsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
