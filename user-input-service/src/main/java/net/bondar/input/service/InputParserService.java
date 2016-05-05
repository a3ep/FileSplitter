package net.bondar.input.service;


import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.ICommandFinder;
import net.bondar.input.interfaces.IInputParserService;
import net.bondar.input.interfaces.IParameterParser;
import net.bondar.input.interfaces.client.ICommand;
import net.bondar.input.interfaces.client.ICommandVerifier;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Provides parsing user input arguments.
 */
public class InputParserService implements IInputParserService {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Command finder.
     */
    private final ICommandFinder commandFinder;

    /**
     * Parameter parser.
     */
    private final IParameterParser parameterParser;

    /**
     * Command verifier.
     */
    private final ICommandVerifier commandVerifier;

    /**
     * Creates <code>InputParserService</code>
     *
     * @param commandFinder   command finder
     * @param parameterParser parameter parser
     */
    public InputParserService(ICommandFinder commandFinder,
                              IParameterParser parameterParser,
                              ICommandVerifier commandVerifier) {
        this.commandFinder = commandFinder;
        this.parameterParser = parameterParser;
        this.commandVerifier = commandVerifier;
    }

    /**
     * Parses user input arguments.
     *
     * @param args user input arguments
     * @return current command
     * @throws ParsingException if received incorrect arguments
     * @see {@link IInputParserService}
     */
    public ICommand parse(final String[] args) throws ParsingException {
        log.info("Start parsing input arguments: " + Arrays.toString(args));
        List<String> argsList = Arrays.asList(args);
        try {
            ICommand currentCommand = commandFinder.findCommand(argsList);
            if (currentCommand.isParametric()) {
                currentCommand.setParameters(parameterParser.parse(argsList.subList(1, argsList.size())));
            }
            if (commandVerifier.verify(currentCommand)) {
                log.info("Finish parsing input arguments. Current command: " + currentCommand.name() + ", parameters: "
                        + currentCommand.getParameters());
                return currentCommand;
            }
        } catch (ParsingException e) {
            log.error("Error while parsing input arguments: " + argsList.toString());
            throw new ParsingException("Error while parsing input arguments: " + argsList.toString(), e);
        }
        log.error("Error incorrect input arguments: " + argsList.toString());
        throw new ParsingException("Error incorrect input arguments: " + argsList.toString());
    }
}
