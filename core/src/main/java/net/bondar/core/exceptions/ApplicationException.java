package net.bondar.core.exceptions;

/**
 * Custom application exception.
 */
public class ApplicationException extends RuntimeException {

    /**
     * Creates <code>ApplicationException</code> instance.
     *
     * @param message the detail error message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Creates <code>ApplicationException</code> instance.
     *
     * @param message the detail error message
     * @param cause the cause
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
