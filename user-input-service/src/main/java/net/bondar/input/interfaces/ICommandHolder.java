package net.bondar.input.interfaces;


import net.bondar.input.interfaces.client.ICommand;

import java.util.List;

/**
 * Provides holding commands.
 */
public interface ICommandHolder {

    /**
     * Gets list of commands.
     *
     * @return list of commands
     */
    List<ICommand> getCommands();
}
