package net.bondar.statistics.interfaces;

import net.bondar.statistics.service.ParameterObject;

import java.util.List;

/**
 * Interface for class that provides holding statistical information parameters.
 */
public interface IParameterObject {

    /**
     * Gets current volume of performed work.
     *
     * @return current volume of performed work
     */
    long getCurrentVolume();

    /**
     * Gets total volume of work.
     *
     * @return total volume of work
     */
    double getTotalVolume();

    /**
     * Gets a list of <code>IParameterObject</code>s.
     *
     * @return a list of <code>IParameterObject</code>s
     */
    List<ParameterObject> getParameterList();
}
