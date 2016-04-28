package net.bondar.input.utils;

import net.bondar.input.interfaces.ICommandHolder;
import net.bondar.input.interfaces.IOptionsHolder;
import net.bondar.input.interfaces.IParameterHolder;
import net.bondar.input.interfaces.client.ICommand;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.commons.cli.Options;

/**
 * Provides holding options.
 */
public class CliOptionsHolder implements IOptionsHolder {

    /**
     * Commands options.
     */
    private final Options commandOptions = new Options();

    /**
     * Parameters options.
     */
    private final Options parameterOptions = new Options();

    /**
     * Creates <code>CliOptionHolder</code> instance.
     *
     * @param commandHolder   command holder
     * @param parameterHolder parameter holder
     */
    public CliOptionsHolder(ICommandHolder commandHolder, IParameterHolder parameterHolder) {
        for (ICommand command : commandHolder.getCommands()) {
            commandOptions.addOption(command.name().toLowerCase(), false, command.getDescription());
        }
        for (IParameter parameter : parameterHolder.getParameters()) {
            parameterOptions.addOption(parameter.getIdentifier().substring(1), true, parameter.getDescription());
        }
    }

    /**
     * Gets command line options.
     *
     * @return command line options
     * @see {@link IOptionsHolder}
     */
    @Override
    public Options getCommandOptions() {
        return commandOptions;
    }

    /**
     * Gets parameters options.
     *
     * @return parameters options
     * @see {@link IOptionsHolder}
     */
    @Override
    public Options getParameterOptions() {
        return parameterOptions;
    }
}
