package net.bondar.core.exceptions;

/**
     * Custom application exception.
 */
public class RunException extends RuntimeException {

    /**
     * Creates <code>RunException</code> instance.
     */
    public RunException() {
        super();
    }

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
     * @param cause   the cause of exception
     */
    public RunException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Creates <code>RunException</code> instance.
     *
     * @param cause the cause of exception
     */
    public RunException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates <code>RunException</code> instance.
     *
     * @param message            the detail error message
     * @param cause              the cause of exception
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    protected RunException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
