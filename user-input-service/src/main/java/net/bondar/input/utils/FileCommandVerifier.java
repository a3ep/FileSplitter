package net.bondar.input.utils;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.input.domain.Command;
import net.bondar.input.domain.Parameter;
import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.ICommandVerifier;
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
     * Configurations holder.
     */
    private final IConfigHolder configHolder;

    /**
     * Creates <code>FileCommandVerifier</code> instance.
     *
     * @param configHolder configurations holder
     */
    public FileCommandVerifier(IConfigHolder configHolder) {
        this.configHolder = configHolder;
    }

    /**
     * Verifies current file command's parameters.
     *
     * @param command the specified command
     * @return true, if verify is successful, else - false
     * @throws ParsingException if received incorrect parameters
     * @see {@link ICommandVerifier}
     */
    @Override
    public boolean verify(Command command) throws ParsingException {
        log.debug("Start verifying parameters. Current command: " + command.name());
        List<Parameter> parameters = command.getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.equals(Parameter.PATH)) {
                Pattern p = Pattern.compile("^[A-Za-z0-9./_]+");
                Matcher m = p.matcher(parameter.getValue());
                if (!m.matches()) {
                    log.error("Error while verifying parameter: " + parameter.getValue());
                    throw new ParsingException("Error while verifying parameter: " + parameter.getValue());
                }
            } else if (parameter.equals(Parameter.SIZE)) {
                Pattern p = Pattern.compile("^[0-9]+");
                Matcher m = p.matcher(parameter.getValue());
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
