/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.hydev.hyritone.task;

import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandException;
import org.hydev.hyritone.Hyritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * TODO: Write a description for this class!
 * <p>
 * Class created by the HyDEV Team on 2020-02-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-02-21 19:47
 */
public class TaskCommand extends Command
{
    /**
     * Creates a new Baritone control command.
     *
     * @param baritone
     * @param names The names of this command. This is what you put after the command prefix.
     */
    public TaskCommand(IBaritone baritone, String... names)
    {
        super(baritone, "task");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException
    {
        if (Hyritone.taskManager.getRunState() != null) Hyritone.taskManager.stop();
        else Hyritone.taskManager.run(TaskPresets.presets.get("AutoEmeralds"));
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) throws CommandException
    {
        return Stream.empty();
    }

    @Override
    public String getShortDesc()
    {
        return "Manage tasks";
    }

    @Override
    public List<String> getLongDesc()
    {
        return Arrays.asList(
            "Manage tasks.",
            "",
            "Usage:",
            "> task "
        );
    }
}
