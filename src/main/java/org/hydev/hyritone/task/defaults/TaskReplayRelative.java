package org.hydev.hyritone.task.defaults;

import lombok.AllArgsConstructor;
import org.hydev.hyritone.task.Task;
import org.hydev.hyritone.task.TaskRunState;

/**
 * Replay from some index
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 20:37
 */
@AllArgsConstructor
public class TaskReplayRelative implements Task
{
    private final int stepOffset;

    @Override
    public void execute(TaskRunState state)
    {
        state.step -= stepOffset + 1;
    }
}
