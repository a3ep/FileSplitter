package net.bondar.splitter.utils;

import net.bondar.input.interfaces.client.ICommand;
import net.bondar.input.interfaces.client.IParameter;

import java.util.ArrayList;
import java.util.List;

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
        this.parameters = parameters;
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

    @Override
    public String toString() {
        return "Command{" +
                "parametric=" + parametric +
                ", parameters=" + parameters +
                ", description='" + description + '\'' +
                '}';
    }
}
