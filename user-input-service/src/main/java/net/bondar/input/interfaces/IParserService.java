package net.bondar.input.interfaces;


import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.ICommand;

/**
 * Provides parsing user input.
 */
public interface IParserService {

    /**
     * Parses user input arguments.
     *
     * @param args user input arguments
     * @return user input command
     * @throws ParsingException if received incorrect arguments
     */
    ICommand parse(final String[] args) throws ParsingException;
}
