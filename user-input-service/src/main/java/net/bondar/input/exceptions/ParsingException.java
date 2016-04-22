package net.bondar.input.exceptions;

import net.bondar.core.exceptions.ApplicationException;

/**
 * Custom parsing exception.
 */
public class ParsingException extends ApplicationException{

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
     * @param cause the cause
     */
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
