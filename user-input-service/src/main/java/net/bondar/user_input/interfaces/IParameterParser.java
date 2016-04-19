package net.bondar.user_input.interfaces;

import net.bondar.user_input.domain.Command;

/**
 * Interface for class that provides parsing user input.
 */
public interface IParameterParser {

    /**
     * Parses user input arguments.
     *
     * @param args user input arguments
     * @return user input command
     */
    Command parse(String[] args);
}
