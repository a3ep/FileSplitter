package net.bondar.input.service;


import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.IParserService;
import net.bondar.input.interfaces.client.ICommand;
import net.bondar.input.utils.CommandFinder;
import net.bondar.input.utils.ParameterParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Provides parsing user input arguments.
 */
public class ParserService implements IParserService {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Command finder.
     */
    private final CommandFinder commandFinder;

    /**
     * Parameter parser.
     */
    private final ParameterParser parameterParser;


    /**
     * Creates <code>ParserService</code>
     *
     * @param commandFinder   command finder
     * @param parameterParser parameter parser
     */
    public ParserService(CommandFinder commandFinder,
                         ParameterParser parameterParser) {
        this.commandFinder = commandFinder;
        this.parameterParser = parameterParser;
    }

    /**
     * Parses user input arguments.
     *
     * @param args user input arguments
     * @return current command
     * @throws ParsingException if received incorrect arguments
     * @see {@link IParserService}
     */
    public ICommand parse(final String[] args) throws ParsingException {
        log.info("Start parsing input arguments: " + Arrays.toString(args));
        List<String> argsList = Arrays.asList(args);
        try {
            ICommand currentCommand = commandFinder.findCommand(argsList.get(0));
            if (currentCommand.isParametric()) {
                currentCommand.setParameters(parameterParser.parse(argsList.subList(1, argsList.size())));
            }
            log.info("Finish parsing input arguments. Current command: " + currentCommand.name() + ", parameters: "
                    + currentCommand.getParameters());
            return currentCommand;
        } catch (ParsingException e) {
            log.error("Error while parsing input arguments: " + argsList.toString());
            throw new ParsingException("Error while parsing input arguments: " + argsList.toString(), e);
        }
    }
}
