package net.bondar.splitter.exceptions;

/**
 * Custom application exception.
 */
public class ApplicationException extends RuntimeException {

    /**
     * Creates <code>CalculatorApplicationException</code> instance.
     *
     * @param message
     */
    public ApplicationException(String message) {
        super(message);
    }
}
