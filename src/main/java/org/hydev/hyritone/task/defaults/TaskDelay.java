package org.hydev.hyritone.task.defaults;

import lombok.AllArgsConstructor;
import org.hydev.hyritone.task.Task;
import org.hydev.hyritone.task.TaskRunState;

/**
 * Task to delay for some seconds
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 18:21
 */
@AllArgsConstructor
public class TaskDelay implements Task
{
    private final long delay;

    @Override
    public void execute(TaskRunState state)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException ignored) {}
    }
}
