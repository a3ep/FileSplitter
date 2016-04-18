package net.bondar.calculations.exceptions;

/**
 * Custom application exception.
 */
public class CalculationsException extends  RuntimeException{

    /**
     * Creates <code>CalculationsException</code> instance.
     *
     * @param message exception message
     */
    public CalculationsException(String message) {
        super(message);
    }
}
