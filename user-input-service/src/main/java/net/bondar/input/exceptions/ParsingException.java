package net.bondar.input.exceptions;

/**
 * Custom parsing exception.
 */
public class ParsingException extends RuntimeException {

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
     * @param cause   the cause
     */
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
