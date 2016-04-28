package net.bondar.input.interfaces;


import net.bondar.input.interfaces.client.ICommand;

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
    ICommand parse(String[] args);
}
