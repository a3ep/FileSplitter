package net.bondar.splitter.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.ICommand;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains user input commands.
 */
public enum Command implements ICommand {

    /**
     * Empty command.
     */
    EMPTY(false, "Indicates empty command."),

    /**
     * Exit command.
     */
    EXIT(false, "Closes application."),

    /**
     * Help command.
     */
    HELP(false, "Shows help message."),

    /**
     * Merge files command.
     */
    MERGE(true, new ArrayList<>(), "Merges files. Example: \"merge -p /file.txt_part_001\"."),

    /**
     * Split file command.
     */
    SPLIT(true, new ArrayList<>(), "Splits file. Example: \"split -p /file.txt -s 1MB\".");

    /**
     * Creates <code>Command</code> instance.
     *
     * @param parametric identifies contains parameters
     * @param parameters list of parameters
     */
    Command(boolean parametric, List<IParameter> parameters, String description) {
        patterns.put(Parameter.PATH.name(), Pattern.compile("^[A-Za-z0-9./_]+"));
        patterns.put(Parameter.SIZE.name(), Pattern.compile("^[0-9]+"));
        this.parametric = parametric;
        this.parameters = parameters;
        this.description = description;
    }

    /**
     * Creates <code>Command</code> instance.
     *
     * @param parametric identifies contains parameters
     */
    Command(final boolean parametric, String description) {
        this.parametric = parametric;
        this.description = description;
    }

    @Override
    public boolean isParametric() {
        return parametric;
    }

    @Override
    public void setParametric(boolean parametric) {
        this.parametric = parametric;
    }

    @Override
    public List<IParameter> getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(List<IParameter> parameters) {
        if (verify(this, parameters)) {
            this.parameters = parameters;
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Verifies current command's parameters.
     *
     * @param command    current command
     * @param parameters setting parameters
     * @return true, if verify is successful, else - false
     * @throws ParsingException if received incorrect parameters
     */
    private boolean verify(ICommand command, List<IParameter> parameters) {
        log.debug("Start verifying parameters. Command: " + command.name());
        for (IParameter parameter : parameters) {
            Matcher matcher = patterns.get(parameter.name()).matcher(parameter.getValue());
            if (!matcher.matches()) {
                log.error("Error while verifying parameter: " + parameter.getValue());
                throw new ParsingException("Error while verifying parameter: " + parameter.getValue());
            }
        }
        log.debug("Finish verifying command's parameters. Command: " + command.name());
        return true;
    }

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Identifies contains parameters.
     */
    private boolean parametric;

    /**
     * List of parameters.
     */
    private List<IParameter> parameters = new ArrayList<>();

    /**
     * Command description.
     */
    private String description;

    /**
     * Map with patterns for verifying parameters.
     */
    private Map<String, Pattern> patterns = new HashMap<>();

    @Override
    public String toString() {
        return "Command{" +
                "parametric=" + parametric +
                ", parameters=" + parameters +
                ", description='" + description + '\'' +
                '}';
    }
}
