package net.bondar.input.interfaces;

import org.apache.commons.cli.Options;

/**
 * Interface for class that provides holding options.
 */
public interface IOptionsHolder {

    /**
     * Gets commands options.
     *
     * @return commands options
     */
    Options getCommandOptions();

    /**
     * Gets parameters options.
     *
     * @return parameters options
     */
    Options getParameterOptions();
}
