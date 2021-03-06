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

package org.hydev.hyritone.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

import static baritone.api.utils.Helper.mc;

/**
 * TODO: Write a description for this class!
 * <p>
 * Class created by the HyDEV Team on 2020-02-19!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-02-19 13:28
 */
public class MiscUtils
{
    public static void print(String text)
    {
        if (mc.player == null) return;

        mc.player.sendMessage(new StringTextComponent("§3§l[§bHyritone§3§l] §r" + text));
        System.out.println("Log: " + text);
    }

    public static void debug(String text)
    {
        print("§8[§7Debug§8]§7 " + text);
    }

    /**
     * Get an identifier for a block pos that's unique for each xyz
     *
     * @param pos Block position
     * @return Stringify
     */
    public static String posId(BlockPos pos)
    {
        return String.format("[%s,%s,%s]", pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Sleep without exceptions
     *
     * @param ms Time in ms
     */
    public static void sleep(long ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
