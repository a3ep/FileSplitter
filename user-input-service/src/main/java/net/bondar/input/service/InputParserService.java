package net.bondar.input.service;


import net.bondar.input.domain.Command;
import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.*;
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
     * Parameter finder.
     */
    private final IParameterFinder parameterFinder;

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
     * @param parameterFinder parameter finder
     * @param parameterParser parameter parser
     */
    public InputParserService(ICommandFinder commandFinder,
                              IParameterFinder parameterFinder,
                              IParameterParser parameterParser,
                              ICommandVerifier commandVerifier) {
        this.commandFinder = commandFinder;
        this.parameterFinder = parameterFinder;
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
    public Command parse(String[] args) throws ParsingException {
        log.debug("Start parsing input: " + Arrays.toString(args));
        List<String> argsList = Arrays.asList(args);
        Command currentCommand = commandFinder.findCommand(argsList);
        if (!currentCommand.isParametric()) {
            log.debug("Finish parsing: " + Arrays.toString(args));
            return currentCommand;
        }
        currentCommand.setParameters(parameterParser.parseParameterValue(parameterFinder.findParameters(argsList.subList(1, argsList.size()))));
        if (commandVerifier.verify(currentCommand)) {
            log.debug("Finish parsing: " + Arrays.toString(args));
            return currentCommand;
        }
        log.error("Error incorrect input arguments: " + argsList.toString());
        throw new ParsingException("Error incorrect input arguments: " + argsList.toString());
    }
}
