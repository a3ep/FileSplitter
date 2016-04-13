package net.bondar.inretfaces;

import net.bondar.Command;

/**
 *
 */
public interface IParametersParser {

    /**
     *
     * @param args
     * @return
     */
    Command parse(String[] args);
}
