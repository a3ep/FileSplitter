package net.bondar.input.exceptions;

/**
 * Custom parsing exception.
 */
public class ParsingException extends RuntimeException {

    /**
     * Creates <code>ParsingException</code> instance.
     */
    public ParsingException() {
        super();
    }

    /**
     * Creates <code>ParsingException</code> instance.
     *
     * @param message the detail error message
     */
    public ParsingException(String message) {
        super(message);
    }

    /**
     * Creates <code>ParsingException</code> instance.
     *
     * @param message the detail error message
     * @param cause   the cause of exception
     */
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates <code>ParsingException</code> instance.
     *
     * @param cause the cause of exception
     */
    public ParsingException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates <code>ParsingException</code> instance.
     *
     * @param message            the detail error message
     * @param cause              the cause of exception
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    protected ParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
