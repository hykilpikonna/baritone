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

package org.hydev.hyritone;

import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hydev.hyritone.MiscUtils.print;

/**
 * TODO: Write a description for this class!
 * <p>
 * Class created by the HyDEV Team on 2020-02-19!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-02-19 12:29
 */
public class TestCommand extends Command
{
    public TestCommand(IBaritone baritone) {
        super(baritone, "seedxray");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException
    {
        if (Hyritone.seedServerCache.enabled = !Hyritone.seedServerCache.enabled)
        {
            print("Seed xray enabled");
        }
        else
        {
            print("Seed xray disabled");
        }
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) {
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "Hyritone: Find ores from the seed server";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
            "This function draws ESP boxes around the ores that exists in the seed server that are not mined.",
            "",
            "Usage:",
            "> seedxray"
        );
    }
}
