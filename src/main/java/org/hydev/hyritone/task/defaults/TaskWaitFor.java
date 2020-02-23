package org.hydev.hyritone.task.defaults;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hydev.hyritone.task.Task;
import org.hydev.hyritone.task.TaskRunState;

import java.util.function.Function;

import static org.hydev.hyritone.utils.MiscUtils.sleep;

/**
 * Wait for a condition to complete
 * <p>
 * Class created by the HyDEV Team on 2020-01-25!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-25 12:29
 */
@Accessors(fluent = true)
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TaskWaitFor implements Task
{
    private final Function<TaskRunState, Boolean> condition;
    private Task onTimeout;
    private long timeout = 10 * 60 * 1000;
    private long interval = 100;

    @Override
    public void execute(TaskRunState state)
    {
        // Timeout detection
        long startTime = System.currentTimeMillis();

        // Run
        while (true)
        {
            if (condition.apply(state)) return;
            sleep(interval);

            if (System.currentTimeMillis() - startTime > timeout)
            {
                // Timeout callback
                if (onTimeout != null)
                {
                    onTimeout.execute(state);
                }
                return;
            }
        }
    }
}
