package org.hydev.hyritone.task;

/**
 * A task waiting to be executed.
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 18:06
 */
public interface Task
{
    /**
     * Execute the task
     */
    void execute(TaskRunState state);

    /**
     * Stop the task
     */
    default void stop()
    {
        // Default nothing to do
    }
}
