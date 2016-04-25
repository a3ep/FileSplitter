package net.bondar.core.exceptions;

/**
 * Custom application exception.
 */
public class RunException extends RuntimeException {

    /**
     * Creates <code>RunException</code> instance.
     *
     * @param message the detail error message
     */
    public RunException(String message) {
        super(message);
    }

    /**
     * Creates <code>RunException</code> instance.
     *
     * @param message the detail error message
     * @param cause the cause
     */
    public RunException(String message, Throwable cause) {
        super(message, cause);
    }
}
