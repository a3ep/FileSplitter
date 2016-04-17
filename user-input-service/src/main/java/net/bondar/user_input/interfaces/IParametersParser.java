package net.bondar.user_input.interfaces;

import net.bondar.user_input.domain.Command;

/**
 *
 */
public interface IParametersParser {

    /**
     * @param args
     * @return
     */
    Command parse(String[] args);
}
