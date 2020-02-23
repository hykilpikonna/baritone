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
import baritone.api.utils.Rotation;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.MerchantContainer;
import org.hydev.hyritone.task.defaults.*;
import org.hydev.hyritone.utils.PlayerUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Long.MAX_VALUE;
import static net.minecraft.inventory.container.ClickType.*;
import static net.minecraft.item.Items.BOOK;
import static net.minecraft.item.Items.BOOKSHELF;
import static org.hydev.hyritone.utils.PlayerUtils.*;

/**
 * TODO: Write a description for this class!
 * <p>
 * Class created by the HyDEV Team on 2020-02-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-02-21 19:48
 */
public class TaskTestCommand extends Command
{
    /**
     * Creates a new Baritone control command.
     *
     * @param baritone
     * @param names The names of this command. This is what you put after the command prefix.
     */
    public TaskTestCommand(IBaritone baritone, String... names)
    {
        super(baritone, "tasktest");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException
    {
        TaskList test = new TaskList("AutoEmeralds",
            // 等待时间在 2000 (8:00) 和 9000 (15:00) 之间w
            new TaskWaitFor(s -> ticks() > 2000 && ticks() < 9000).timeout(MAX_VALUE),

            // 转向村民
            s -> PlayerUtils.rotate(new Rotation(-90, 0)), // 转向村民w
            new TaskDelay(10), // 等w

            // 打开村民交易w
            s -> mouse().rightClick(), // 点击村民w
            new TaskWaitFor(s -> player().openContainer instanceof MerchantContainer) // 等点击完成w
                .onTimeout(new TaskReplayRelative(1)), // 如果失败就重来w

            // 和村民交易w (绿宝石 -> 书架)
            s -> controller().windowClick(containerId(), 36, 0, PICKUP, player()), // 拿起绿宝石w
            new TaskDelay(1000), // 等w
            s -> controller().windowClick(containerId(), 0, 0, PICKUP, player()), // 把书放到交易框w
            new TaskDelay(1000), // 等w
            s -> controller().windowClick(containerId(), 2, 0, QUICK_MOVE, player()), // 把书架移到 8
            new TaskDelay(1000), // 等w
            s -> player().closeScreen(), // 关掉界面w

            // 把书架转换回书w
            s -> PlayerUtils.rotate(new Rotation(90, 0)), // 转到后面面向黑曜石w
            s -> player().inventory.currentItem = 8, // 选择 1: 书架w
            s -> mouse().rightClick(), // 放书架w
            new TaskDelay(1000), // 等w
            s -> player().inventory.currentItem = 0, // 选择 0: 斧子w
            s -> PlayerUtils.dig(getLookingAtBlock()), // 挖掉w
            new TaskDelay(1000), // 等w
            new TaskCondition(s -> !player().inventory.hasAny(new HashSet<>(Collections.singletonList(BOOKSHELF))))
                .taskElse(new TaskReplayRelative(5 + 2)), // 如果没有书架了就继续, 有的话就回到 5 步之前w

            // 转向村民
            s -> PlayerUtils.rotate(new Rotation(-90, 0)), // 转向村民w
            new TaskDelay(10), // 等w

            // 打开村民交易w
            s -> mouse().rightClick(), // 点击村民w
            new TaskWaitFor(s -> player().openContainer instanceof MerchantContainer) // 等点击完成w
                .onTimeout(new TaskReplayRelative(1)), // 如果失败就重来w

            // 和村民交易w (书 -> 绿宝石)
            s -> controller().windowClick(containerId(), 31, 0, PICKUP, player()), // 拿起书
            s -> controller().windowClick(containerId(), 0, 0, PICKUP, player()), // 把书放到交易框w
            new TaskDelay(1000), // 等w
            s -> controller().windowClick(containerId(), 2, 0, QUICK_MOVE, player()), // 把绿宝石移到 8
            new TaskDelay(1000), // 等w
            s -> controller().windowClick(containerId(), 0, 0, PICKUP, player()), // 拿起剩下的书
            s -> controller().windowClick(containerId(), 31, 0, PICKUP, player()), // 在 1 放下书
            new TaskDelay(1000), // 等w
            s -> player().closeScreen(), // 关掉界面w
            new TaskDelay(5000), // 等村民补充w
            new TaskCondition(s -> player().inventory.hasAny(new HashSet<>(Collections.singletonList(BOOK))))
                .taskElse(new TaskReplayRelative(9 + 3)), // 如果没有书了就继续, 有的话就回到 10 步之前w

            // 转向箱子w
            s -> PlayerUtils.rotate(new Rotation(180, 0)), // 转向箱子w
            new TaskDelay(10), // 等w

            // 打开箱子w
            s -> mouse().rightClick(), // 点击箱子w
            new TaskWaitFor(s -> player().openContainer instanceof ChestContainer) // 等点击完成w
                .onTimeout(new TaskReplayRelative(1)), // 如果失败就重来w

            // 把多余的绿宝石放到箱子里w
            s -> controller().windowClick(containerId(), 89, 0, PICKUP, player()), // 拿起 36 个绿宝石w
            s -> controller().windowClick(containerId(), -999, 0, QUICK_CRAFT, player()), // 开始快速合成w
            s -> controller().windowClick(containerId(), 89, 1, QUICK_CRAFT, player()), // 在 8 放 1/3
            s -> controller().windowClick(containerId(), 88, 1, QUICK_CRAFT, player()), // 在 7 放 1/3
            s -> controller().windowClick(containerId(), 87, 1, QUICK_CRAFT, player()), // 在 6 放 1/3
            s -> controller().windowClick(containerId(), -999, 2, QUICK_CRAFT, player()), // 结束快速合成w
            s -> controller().windowClick(containerId(), 88, 0, QUICK_MOVE, player()), // 把 7 的绿宝石移到箱子里w
            s -> controller().windowClick(containerId(), 89, 0, QUICK_MOVE, player()), // 把 8 的绿宝石移到箱子里w
            s -> player().closeScreen(), // 关闭箱子w

            // 从头开始w
            new TaskReplay(0)
        );

        TaskPresets.presets.clear();
        TaskPresets.presets.put("AutoEmeralds", test);
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) throws CommandException
    {
        return Stream.empty();
    }

    @Override
    public String getShortDesc()
    {
        return "Add testing task";
    }

    @Override
    public List<String> getLongDesc()
    {
        return Arrays.asList(
            "Add testing task.",
            "",
            "Usage:",
            "> tasktest "
        );
    }
}
