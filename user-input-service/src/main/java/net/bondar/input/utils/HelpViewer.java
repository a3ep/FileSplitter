package net.bondar.input.utils;

import org.apache.commons.cli.HelpFormatter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides displaying help message.
 */
public class HelpViewer {

    /**
     * String with commands options description.
     */
    private static final String COMMANDS_OPTIONS_DESCRIPTION = "===Commands Options===";

    /**
     * String with parameters options description.
     */
    private static final String PARAMETERS_OPTIONS_DESCRIPTION = "===Parameters Options===";

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Help formatter.
     */
    private final HelpFormatter helpFormatter = new HelpFormatter();

    /**
     * Options holder.
     */
    private final CliOptionsHolder optionsHolder;

    /**
     * Creates <code>HelpViewer</code> instance.
     *
     * @param optionsHolder options holder
     */
    public HelpViewer(CliOptionsHolder optionsHolder) {
        this.optionsHolder = optionsHolder;
    }

    /**
     * Shows help message.
     *
     */
    public void showHelp() {
        log.debug("Showing help.");
        helpFormatter.printHelp(COMMANDS_OPTIONS_DESCRIPTION, optionsHolder.getCommands());
        helpFormatter.printHelp(PARAMETERS_OPTIONS_DESCRIPTION, optionsHolder.getParameters());
    }
}
