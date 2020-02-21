package org.hydev.hyritone.task;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO: Write a description for this class!
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 20:38
 */
@Data
@AllArgsConstructor
public class TaskRunState
{
    public Thread thread;
    public TaskList taskList;
    public int step;

    @Override
    public String toString()
    {
        return "Task " + thread.getId() + " is executing step " + step + " of " + taskList.size() + " " +
            "| Current Task: " + taskList.get(step).getClass().getSimpleName();
    }
}
