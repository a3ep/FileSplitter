package net.bondar.input.interfaces.client;

import net.bondar.input.exceptions.ParsingException;

/**
 * Interface for class that provides verifying command's parameters.
 */
public interface ICommandVerifier {

    /**
     * Verifies command's parameters.
     *
     * @param command the specified command
     * @return true, if verify is successful, else - false
     * @throws ParsingException if received incorrect parameters
     */
    boolean verify(ICommand command) throws ParsingException;
}
