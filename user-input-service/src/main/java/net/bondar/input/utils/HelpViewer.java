package net.bondar.input.utils;

import net.bondar.input.interfaces.IHelpViewer;
import net.bondar.input.interfaces.IOptionsHolder;
import org.apache.commons.cli.HelpFormatter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides displaying help message.
 */
public class HelpViewer implements IHelpViewer {

    /**
     * String with commands options description.
     */
    private static final String COMMANDS_OPTIONS_DESCRIPTION = "===FileSplitter Commands Options===";

    /**
     * String with parameters options description.
     */
    private static final String PARAMETERS_OPTIONS_DESCRIPTION = "===FileSplitter Parameters Options===";

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
    private final IOptionsHolder optionsHolder;

    /**
     * Creates <code>HelpViewer</code> instance.
     *
     * @param optionsHolder options holder
     * @see {@link IHelpViewer}
     */
    public HelpViewer(IOptionsHolder optionsHolder) {
        this.optionsHolder = optionsHolder;
    }

    /**
     * Shows help message.
     *
     * @see {@link IHelpViewer}
     */
    @Override
    public void showHelp() {
        log.debug("Showing help.");
        helpFormatter.printHelp(COMMANDS_OPTIONS_DESCRIPTION, optionsHolder.getCommandOptions());
        helpFormatter.printHelp(PARAMETERS_OPTIONS_DESCRIPTION, optionsHolder.getParameterOptions());
    }
}
