package org.hydev.hyritone.task.defaults;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
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
 * o@since 2020-01-25 17:44
 */
@Setter
@Accessors(fluent = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class TaskCondition implements Task
{
    private final Function<TaskRunState, Boolean> condition;
    private Task taskThen;
    private Task taskElse;

    @Override
    public void execute(TaskRunState state)
    {
        if (condition.apply(state))
        {
            if (taskThen != null)
            {
                taskThen.execute(state);
            }
        }
        else if (taskElse != null)
        {
            taskElse.execute(state);
        }
    }
}
