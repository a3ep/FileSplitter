package net.bondar.input.interfaces.client;

import java.util.List;

/**
 * Interface for a enum that contains commands.
 */
public interface ICommand {

    /**
     * Gets command name.
     *
     * @return command name
     */
    String name();

    /**
     * Gets parametric flag.
     *
     * @return parametric flag
     */
    boolean isParametric();

    /**
     * Gets parameters.
     *
     * @return list of parameters
     */
    List<IParameter> getParameters();

    /**
     * Sets parameters.
     *
     * @param parameters list of parameters
     */
    void setParameters(List<IParameter> parameters);

    /**
     * Gets command description.
     *
     * @return command description
     */
    String getDescription();
}
