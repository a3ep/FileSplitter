package net.bondar.splitter.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.ICommand;
import net.bondar.input.interfaces.client.ICommandVerifier;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides verifying file command's parameters.
 */
public class FileCommandVerifier implements ICommandVerifier {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Path pattern.
     */
    private final Pattern pathPattern = Pattern.compile("^[A-Za-z0-9./_]+");

    /**
     * Size pattern.
     */
    private final Pattern sizePattern = Pattern.compile("^[0-9]+");
    /**
     * Verifies current file command's parameters.
     *
     * @param command the specified command
     * @return true, if verify is successful, else - false
     * @throws ParsingException if received incorrect parameters
     * @see {@link ICommandVerifier}
     */
    @Override
    public boolean verify(final ICommand command) throws ParsingException {
        log.debug("Start verifying parameters. Current command: " + command.name());
        if(!command.isParametric()){
            return true;
        }
        for (IParameter parameter : command.getParameters()) {
            if (parameter.equals(Parameter.PATH)) {
                Matcher m = pathPattern.matcher(parameter.getValue());
                if (!m.matches()) {
                    log.error("Error while verifying parameter: " + parameter.getValue());
                    throw new ParsingException("Error while verifying parameter: " + parameter.getValue());
                }
            } else if (parameter.equals(Parameter.SIZE)) {
                Matcher m = sizePattern.matcher(parameter.getValue());
                if (!m.matches()) {
                    log.error("Error while verifying parameter: " + parameter.getValue());
                    throw new ParsingException("Error while verifying parameter: " + parameter.getValue());
                }
            }
        }
        log.debug("Finish verifying command's parameters. Current command: " + command.name());
        return true;
    }
}
