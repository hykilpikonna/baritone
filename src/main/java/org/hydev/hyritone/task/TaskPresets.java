package org.hydev.hyritone.task;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.hydev.hyritone.Main;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.hydev.hyritone.utils.ScriptUtils.getEngine;

/**
 * This class contains preset tasks
 * TODO: Make a UI for task list!
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 18:23
 */
public class TaskPresets
{
    // Map<name.toLowerCase(), taskList>
    public static Map<String, TaskList> presets = new HashMap<>();

    /**
     * Register a task list
     *
     * @param list Task list
     */
    public static void register(TaskList list)
    {
        presets.put(list.name.toLowerCase(), list);
    }

    /**
     * Get preset by name
     *
     * @param name Lowercase name
     * @return Task list by name, null if not found
     */
    public static TaskList getPreset(String name)
    {
        if (presets.containsKey(name.toLowerCase()))
        {
            return presets.get(name.toLowerCase());
        }
        return null;
    }

    /**
     * Load presets from file
     */
    @SneakyThrows
    public static void reloadPresets()
    {
        Path scriptDirPath = Main.taskPresetsDir.toPath();

        presets.clear();

        try
        {
            Files.walk(scriptDirPath, FileVisitOption.FOLLOW_LINKS)
                .filter(Files::isRegularFile)
                .forEach(path ->
                {
                    try
                    {
                        String script = FileUtils.readFileToString(path.toFile(), StandardCharsets.UTF_8);
                        TaskList list = (TaskList) getEngine().eval(script);
                        presets.put(list.name.toLowerCase(), list);
                    }
                    catch (IOException e)
                    {
                        throw new UncheckedIOException(e);
                    }
                    catch (ScriptException e)
                    {
                        e.printStackTrace();
                    }
                });
        }
        catch (IOException | UncheckedIOException e)
        {
            System.err.println("Error when loading hyritone scripts.");
            e.printStackTrace();
        }
    }
}
