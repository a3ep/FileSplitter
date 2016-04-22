package net.bondar.input.utils;

import net.bondar.input.domain.Command;
import net.bondar.input.domain.Parameter;
import net.bondar.input.interfaces.IOptionsHolder;
import org.apache.commons.cli.Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Provides holding options.
 */
public class CliOptionsHolder implements IOptionsHolder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

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
     */
    public CliOptionsHolder() {
        List<Command> commands = Arrays.asList(Command.values());
        for (Command command : commands) {
            commandOptions.addOption(command.name().toLowerCase(), false, command.getDescription());
        }
        List<Parameter> parameters = Arrays.asList(Parameter.values());
        for (Parameter parameter : parameters) {
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
        log.debug("Getting command line options.");
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
