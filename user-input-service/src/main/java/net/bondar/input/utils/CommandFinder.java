package net.bondar.input.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.ICommand;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides finding commands.
 */
public class CommandFinder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Command holder.
     */
    private final CommandHolder commandHolder;

    /**
     * Creates <code>CommandFinder</code> instance.
     *
     * @param commandHolder command holder
     */
    public CommandFinder(CommandHolder commandHolder) {
        this.commandHolder = commandHolder;
    }

    /**
     * Finds command in the specified array of strings.
     *
     * @param commandString first string of input
     * @return current command
     * @throws ParsingException if command is not found
     */
    public ICommand findCommand(final String commandString) throws ParsingException {
        log.debug("Finding current command.");
        for (ICommand command : commandHolder.getCommands()) {
            if (commandString.equals(command.name().toLowerCase())) {
                log.debug("Current command: " + command.name());
                return command;
            }
        }
        log.error("Error while finding command.");
        throw new ParsingException("Error while finding command in " + commandString);
    }
}
