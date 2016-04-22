package net.bondar.input.interfaces;


import net.bondar.input.domain.Command;

/**
 * Interface for class that provides parsing user input.
 */
public interface IInputParserService {

    /**
     * Parses user input arguments.
     *
     * @param args user input arguments
     * @return user input command
     */
    Command parse(String[] args);
}
