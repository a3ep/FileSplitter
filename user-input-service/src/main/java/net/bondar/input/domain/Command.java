package net.bondar.input.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains user input commands.
 */
public enum Command {

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
    Command(boolean parametric, List<Parameter> parameters, String description) {
        this.parametric = parametric;
        this.parameters = parameters;
        this.description = description;
    }

    /**
     * Creates <code>Command</code> instance.
     *
     * @param parametric identifies contains parameters
     */
    Command(boolean parametric, String description) {
        this.parametric = parametric;
        this.description = description;
    }

    /**
     * Gets parametric flag.
     *
     * @return parametric flag
     */
    public boolean isParametric() {
        return parametric;
    }

    /**
     * Gets parameters.
     *
     * @return list of parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Sets parameters.
     *
     * @param parameters setting list of patameters
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets command description.
     *
     * @return command description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Identifies contains parameters.
     */
    private boolean parametric;

    /**
     * List of parameters.
     */
    private List<Parameter> parameters = new ArrayList<>();

    /**
     * Command description.
     */
    private String description;
}
