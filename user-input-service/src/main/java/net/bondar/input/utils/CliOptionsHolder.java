package net.bondar.input.utils;

import net.bondar.input.interfaces.client.ICommand;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.commons.cli.Options;

/**
 * Provides holding options.
 */
public class CliOptionsHolder {

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
    private final CommandHolder commandHolder;

    /**
     * Parameters holder.
     */
    private final ParameterHolder parameterHolder;

    /**
     * Creates <code>CliOptionHolder</code> instance.
     *
     * @param commandHolder   commands holder
     * @param parameterHolder parameters holder
     */
    public CliOptionsHolder(CommandHolder commandHolder, ParameterHolder parameterHolder) {
        this.commandHolder = commandHolder;
        this.parameterHolder = parameterHolder;
        setCommands(createCommands());
        setParameters(createParameters());
    }

    /**
     * Gets command line options.
     *
     * @return command line options
     */
    public Options getCommands() {
        return commands;
    }

    /**
     * Sets commands options.
     *
     * @param commands setting commands options
     */
    public void setCommands(Options commands) {
        this.commands = commands;
    }

    /**
     * Gets parameters options.
     *
     * @return parameters options
     */
    public Options getParameters() {
        return parameters;
    }

    /**
     * Sets parameters options.
     *
     * @param parameters setting parameters options
     */
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
