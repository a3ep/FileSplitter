package net.bondar.input.interfaces;


import net.bondar.input.domain.Command;

import java.util.List;

/**
 * Interface for class that provides holding commands.
 */
public interface ICommandHolder {

    /**
     * Gets list of commands.
     *
     * @return list of commands
     */
    List<Command> getCommands();
}
