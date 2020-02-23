package org.hydev.hyritone.task.defaults;

import lombok.AllArgsConstructor;
import net.minecraft.util.math.BlockPos;
import org.hydev.hyritone.task.Task;
import org.hydev.hyritone.task.TaskRunState;
import org.hydev.hyritone.utils.PlayerUtils;

/**
 * Interact with blocks (Eg. chests)
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 23:10
 */
@AllArgsConstructor
public class TaskInteract implements Task
{
    private final long x;
    private final long y;
    private final long z;

    @Override
    public void execute(TaskRunState state)
    {
        PlayerUtils.interact(new BlockPos(x, y, z));
    }
}
