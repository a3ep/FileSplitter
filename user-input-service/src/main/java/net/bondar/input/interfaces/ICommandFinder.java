package net.bondar.input.interfaces;

import net.bondar.input.interfaces.client.ICommand;

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
    ICommand findCommand(List<String> list);

}
