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
    private Options commands = new Options();

    /**
     * Parameters options.
     */
    private Options parameters = new Options();

    /**
     * Commands holder.
     */
    private final ICommandHolder commandHolder;

    /**
     * Parameters holder.
     */
    private final IParameterHolder parameterHolder;

    /**
     * Creates <code>CliOptionHolder</code> instance.
     *
     * @param commandHolder   commands holder
     * @param parameterHolder parameters holder
     */
    public CliOptionsHolder(ICommandHolder commandHolder, IParameterHolder parameterHolder) {
        this.commandHolder = commandHolder;
        this.parameterHolder = parameterHolder;
        setCommands(createCommands());
        setParameters(createParameters());
    }

    /**
     * Gets command line options.
     *
     * @return command line options
     * @see {@link IOptionsHolder}
     */
    public Options getCommands() {
        return commands;
    }

    /**
     * Sets commands options.
     *
     * @param commands setting commands options
     */
    @Override
    public void setCommands(Options commands) {
        for (ICommand command : commandHolder.getCommands()) {
            commands.addOption(command.name().toLowerCase(), false, command.getDescription());
        }
    }

    /**
     * Gets parameters options.
     *
     * @return parameters options
     * @see {@link IOptionsHolder}
     */
    public Options getParameters() {
        return parameters;
    }

    /**
     * Sets parameters options.
     *
     * @param parameters setting parameters options
     */
    @Override
    public void setParameters(Options parameters) {
        this.parameters = parameters;
    }

    /**
     * Creates commands options.
     *
     * @return commands options
     */
    private Options createCommands(){
        Options commands = new Options();
        for (ICommand command : commandHolder.getCommands()) {
            commands.addOption(command.name().toLowerCase(), false, command.getDescription());
        }
        return commands;
    }

    /**
     * Creates parameters options.
     *
     * @return parameters options
     */
    private Options createParameters(){
        Options parameters = new Options();
        for (IParameter parameter : parameterHolder.getParameters()) {
            parameters.addOption(parameter.getIdentifier().substring(1), true, parameter.getDescription());
        }
        return parameters;
    }
}
