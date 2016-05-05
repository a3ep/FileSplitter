package net.bondar.input.interfaces;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.ICommand;

import java.util.List;

/**
 * Provides finding command.
 */
public interface ICommandFinder {

    /**
     * Finds command.
     *
     * @param list list of arguments
     * @return current command
     * @throws ParsingException if command is not found
     */
    ICommand findCommand(final List<String> list) throws ParsingException;

}
