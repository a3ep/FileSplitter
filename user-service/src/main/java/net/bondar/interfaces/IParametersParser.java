package net.bondar.interfaces;

import net.bondar.domain.Command;

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
