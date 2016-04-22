package net.bondar.input.utils;

import net.bondar.input.domain.Command;
import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.ICommandFinder;
import net.bondar.input.interfaces.ICommandHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Provides finding file command.
 */
public class FileCommandFinder implements ICommandFinder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Command holder.
     */
    private final ICommandHolder commandHolder;

    /**
     * Creates <code>FileCommandFinder</code> instance.
     *
     * @param commandHolder command holder
     */
    public FileCommandFinder(ICommandHolder commandHolder) {
        this.commandHolder = commandHolder;
    }

    /**
     * Finds command in the specified array of strings.
     *
     * @param array specified array of strings
     * @return current command
     * @throws ParsingException if command is not found
     * @see {@link ICommandHolder}
     */
    @Override
    public Command findCommand(List<String> array) throws ParsingException {
        log.debug("Finding current command.");
        for (Command command : commandHolder.getCommands()) {
            if (array.contains(command.name().toLowerCase())) {
                log.debug("Current command: " + command.name());
                return command;
            }
        }
        log.error("Error while finding command.");
        throw new ParsingException("Error while finding command in " + array.toString());
    }
}
