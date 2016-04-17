package net.bondar.interfaces;

import java.util.Map;

/**
 *
 */
public interface IStatisticHolder {

    Map<String, IPart> getStatistic();

    void putInformation(String key, IPart filePart);
}
