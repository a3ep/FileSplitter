package net.bondar.input.interfaces;

import net.bondar.input.domain.Command;

import java.util.List;

/**
 * Interface for class that provides finding command.
 */
public interface ICommandFinder {

    /**
     * Finds command.
     *
     * @param list list of arguments
     * @return current command
     */
    Command findCommand(List<String> list);

}
