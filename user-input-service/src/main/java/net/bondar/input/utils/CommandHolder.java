package net.bondar.input.utils;

import net.bondar.input.interfaces.client.ICommand;

import java.util.List;

/**
 * Provides holding commands.
 */
public class CommandHolder {

    /**
     * List of commands.
     */
    private final List<ICommand> commands;

    /**
     * Creates <code>CommandHolder</code> instance.
     *
     * @param commands list of commands
     */
    public CommandHolder(List<ICommand> commands) {
        this.commands = commands;
    }

    /**
     * Gets list of commands.
     *
     * @return list of commands
     */
    public List<ICommand> getCommands() {
        return commands;
    }
}
