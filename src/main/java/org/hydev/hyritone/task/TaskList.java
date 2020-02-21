package org.hydev.hyritone.task;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Instances of this class contains lists of tasks to be executed in order.
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 18:05
 */
@AllArgsConstructor
public class TaskList extends ArrayList<Task>
{
    public final String name;

    public TaskList(String name, Task... tasks)
    {
        this(name);
        Collections.addAll(this, tasks);
    }
}
