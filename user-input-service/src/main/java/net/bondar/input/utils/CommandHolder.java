package net.bondar.input.utils;

import net.bondar.input.domain.Command;
import net.bondar.input.interfaces.ICommandHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Provides holding commands.
 */
public class CommandHolder implements ICommandHolder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * List of commands.
     */
    private final List<Command> commands = Arrays.asList(Command.values());

    /**
     * Gets list of commands.
     *
     * @return list of commands
     * @see {@link ICommandHolder}
     */
    @Override
    public List<Command> getCommands() {
        return commands;
    }
}
