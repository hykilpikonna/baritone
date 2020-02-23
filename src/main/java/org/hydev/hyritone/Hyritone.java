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

import baritone.Baritone;
import org.hydev.hyritone.seedxray.SeedServerCache;
import org.hydev.hyritone.task.TaskManager;

/**
 * TODO: Write a description for this class!
 * <p>
 * Class created by the HyDEV Team on 2020-02-19!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-02-19 13:42
 */
public class Hyritone
{
    public static SeedServerCache seedServerCache;
    public static TaskManager taskManager;

    public Hyritone(Baritone baritone)
    {
        seedServerCache = new SeedServerCache(baritone);
        taskManager = new TaskManager();
    }
}
