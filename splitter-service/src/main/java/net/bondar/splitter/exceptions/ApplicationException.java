package net.bondar.splitter.exceptions;

/**
 * Custom application exception.
 */
public class ApplicationException extends RuntimeException {

    /**
     * Creates <code>CalculatorApplicationException</code> instance.
     *
     * @param message exception message
     */
    public ApplicationException(String message) {
        super(message);
    }
}
