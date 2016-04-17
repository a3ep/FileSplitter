package net.bondar.statistics.interfaces;

import java.util.Map;

/**
 *
 */
public interface IStatisticHolder {

    Map<String, IPartObject> getStatistic();

    void putInformation(String key, IPartObject filePart);
}
