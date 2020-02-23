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

package org.hydev.hyritone.seedxray;

import lombok.AllArgsConstructor;

/**
 * TODO: Write a description for this class!
 * <p>
 * Class created by the HyDEV Team on 2020-02-23!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-02-23 11:32
 */
@AllArgsConstructor
public enum SeedXrayProperties
{
    DIAMOND("diamond", "diamond_ore", 3, 16),
    IRON("iron", "iron_ore", 20, 60),
    REDSTONE("redstone", "redstone_ore", 3, 16),
    LAPIS("lapis", "lapis_lazuri_ore", 5, 30),
    GOLD("gold", "gold_ore", 5, 30),
    GOLD_BADLANDS("gold_badlands", "gold_ore", 32, 70),
    EMERALD("emerald", "emerald_ore", 5, 30);

    public String name;
    public String blockToFind;
    public int yMin;
    public int yMax;

    public static SeedXrayProperties byName(String name)
    {
        name = name.toLowerCase().replace("-", "_");

        for (SeedXrayProperties value : SeedXrayProperties.values())
        {
            if (value.name.equals(name) || value.blockToFind.equals(name))
            {
                return value;
            }
        }

        return null;
    }
}
