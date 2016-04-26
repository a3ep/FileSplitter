package net.bondar.calculations.exceptions;

/**
 * Custom application exception.
 */
public class CalculationsException extends RuntimeException {

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
     * @param cause   the cause
     */
    public CalculationsException(String message, Throwable cause) {
        super(message, cause);
    }
}
