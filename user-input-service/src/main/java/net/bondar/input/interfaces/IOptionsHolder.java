package net.bondar.input.interfaces;

import org.apache.commons.cli.Options;

/**
 * Provides holding options.
 */
public interface IOptionsHolder {

    /**
     * Gets commands options.
     *
     * @return commands options
     */
    Options getCommands();

    /**
     * Sets commands options.
     *
     * @param commands setting commands options
     */
    void setCommands(Options commands);

    /**
     * Gets parameters options.
     *
     * @return parameters options
     */
    Options getParameters();

    /**
     * Sets parameters options.
     *
     * @param parameters setting parameters options
     */
    void setParameters(Options parameters);
}
