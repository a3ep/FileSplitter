package net.bondar.input.interfaces;

import net.bondar.input.domain.Command;

/**
 * Interface for class that provides verifying command's parameters.
 */
public interface ICommandVerifier {

    /**
     * Verifies command's parameters.
     *
     * @param command the specified command
     * @return true, if verify is successful, else - false
     */
    boolean verify(Command command);
}
