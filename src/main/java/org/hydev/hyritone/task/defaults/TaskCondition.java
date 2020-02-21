package org.hydev.hyritone.task.defaults;

import lombok.AllArgsConstructor;
import org.hydev.hyritone.task.Task;
import org.hydev.hyritone.task.TaskRunState;

import java.util.function.Function;

/**
 * Run task if condition is met
 * <p>
 * Class created by the HyDEV Team on 2020-01-25!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-25 17:44
 */
@AllArgsConstructor
public class TaskCondition implements Task
{
    private final Function<TaskRunState, Boolean> condition;
    private final Task taskIf;
    private final Task taskElse;

    @Override
    public void execute(TaskRunState state)
    {
        if (condition.apply(state))
        {
            if (taskIf != null)
            {
                taskIf.execute(state);
            }
        }
        else if (taskElse != null)
        {
            taskElse.execute(state);
        }
    }
}
