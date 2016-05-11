package net.bondar.core.utils.factories;

import net.bondar.core.interfaces.tasks.ICloseTask;
import net.bondar.core.tasks.CloseTask;
import net.bondar.core.utils.FileProcessor;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Provides creating application closable.
 */
public class CloseTaskFactory {

    /**
     * Creates concrete cleaning-closing task.
     *
     * @param processor    file processor
     * @param futures      list of futures that represented threads in a pool
     * @return <code>CloseTask</code> instance
     */
    public ICloseTask createCloseTask(FileProcessor processor, final List<Future> futures) {
        return new CloseTask(processor, futures);
    }
}
