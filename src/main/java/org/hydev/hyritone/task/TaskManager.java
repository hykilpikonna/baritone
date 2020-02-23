package org.hydev.hyritone.task;

import lombok.Getter;

import static org.hydev.hyritone.utils.MiscUtils.debug;

/**
 * This class is used to manage tasks.
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 19:03
 */
@Getter
public class TaskManager
{
    private TaskRunState runState = null;

    /**
     * Run a task list
     *
     * @param list Task list
     * @return Successfully started or not
     */
    public boolean run(TaskList list)
    {
        // Check already started
        if (runState != null)
        {
            stop();
        }

        // Reset values
        runState = new TaskRunState(null, list, 0);

       /* // Execute task
        Baritone.getExecutor().execute(() ->
        {
            // Execute step by step
            while (runState.step < list.size())
            {
                list.get(runState.step).execute(runState);
                runState.step++;
            }

            // Finished
            stop();
        });*/

        // Create thread
        runState.thread = new Thread(() ->
        {
            // Execute step by step
            while (runState.step < list.size())
            {
                debug("Running step " + runState);
                list.get(runState.step).execute(runState);
                runState.step++;
            }

            // Finished
            stop();
        }, "Hyritone Task");

        // Start async
        runState.thread.start();

        // Success
        return true;
    }

    /**
     * Stop the task list
     */
    public void stop()
    {
        // Not started
        if (runState == null) return;

        // Stop thread
        runState.thread.interrupt();

        // Stop task
        if (runState.getStep() < runState.getTaskList().size())
        {
            runState.getTaskList().get(runState.getStep()).stop();
        }

        runState = null;
    }

    // TODO: pause
}
