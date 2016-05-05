package net.bondar.input.interfaces.client;

import net.bondar.input.exceptions.ParsingException;

/**
 * Provides verifying command's parameters.
 */
public interface ICommandVerifier {

    /**
     * Verifies command's parameters.
     *
     * @param command the specified command
     * @return true, if verify is successful, else - false
     * @throws ParsingException if received incorrect parameters
     */
    boolean verify(final ICommand command) throws ParsingException;
}
