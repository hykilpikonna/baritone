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
import baritone.api.event.events.RenderEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.behavior.Behavior;
import baritone.utils.PathRenderer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static baritone.api.utils.Helper.mc;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hydev.hyritone.MiscUtils.debug;

/**
 * TODO: Write a description for this class!
 * <p>
 * Class created by the HyDEV Team on 2020-02-19!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-02-19 12:35
 */
public class SeedServerCache extends Behavior
{
    public static CloseableHttpClient http = HttpClients.createDefault();

    public BetterBlockPos cacheLocation;
    public List<BlockPos> cacheBlocks = new ArrayList<>();
    private static final Type blocksType = new TypeToken<ArrayList<BlockPos>>(){}.getType();

    public boolean enabled = false;
    public String blockToFind = "diamond_ores";
    public boolean updating = false;

    public int ticks = 0;

    protected SeedServerCache(Baritone baritone)
    {
        super(baritone);
    }

    /**
     * Check for updates every tick
     *
     * @param event Tick event
     */
    @Override
    public void onTick(TickEvent event)
    {
        if (!enabled || updating) return;

        BetterBlockPos feet = new BetterBlockPos(mc.player.getPositionVec().x, mc.player.getPositionVec().y + 0.1251, mc.player.getPositionVec().z);

        if (cacheLocation == null)
        {
            new Thread(this::updateCache).start();
        }

        // Tell the server to load chunks every 20 seconds (20 * 20 = 400 ticks)
        ticks ++;

        if (ticks > 400)
        {
            ticks = 0;
            new Thread(this::loadChunks).start();
        }
    }

    /**
     * Tell the server to pre-load the chunks
     */
    public void loadChunks()
    {
        BetterBlockPos feet = new BetterBlockPos(mc.player.getPositionVec().x, mc.player.getPositionVec().y + 0.1251, mc.player.getPositionVec().z);

        // Get request
        HttpGet get = new HttpGet("http://localhost:12255/api/load-chunks");
        get.setHeader("world", "world");
        get.setHeader("center-x", "" + feet.getX());
        get.setHeader("center-z", "" + feet.getZ());
        get.setHeader("max-search-rad", "64");

        try (CloseableHttpResponse response = http.execute(get))
        {
            debug("Telling seed exploit server to load chunk...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Update cached blocks
     */
    public void updateCache()
    {
        // Prevent duplicate calls to update
        updating = true;

        BetterBlockPos feet = new BetterBlockPos(mc.player.getPositionVec().x, mc.player.getPositionVec().y + 0.1251, mc.player.getPositionVec().z);

        // Get request
        HttpGet get = new HttpGet("http://localhost:12255/api/get-locations-of");
        get.setHeader("world", "world");
        get.setHeader("block", "diamond_ore");
        get.setHeader("maximum", "64");
        get.setHeader("center-x", "" + feet.getX());
        get.setHeader("center-y", "" + feet.getY());
        get.setHeader("center-z", "" + feet.getZ());
        get.setHeader("max-search-rad", "64");
        get.setHeader("y-min", "3");
        get.setHeader("y-max", "16");

        debug("==============================================");
        debug("Getting block data from seed exploit server...");

        try (CloseableHttpResponse response = http.execute(get))
        {
            ArrayList<BlockPos> blocks = new Gson().fromJson(IOUtils.toString(response.getEntity().getContent(), UTF_8), blocksType);

            // Clear unwanted blocks
            blocks.forEach(block ->
            {
                // Exited
                if (mc.world == null) return;

                debug("Removing air...");

                // Only add it if it is not air in the client world
                if (!mc.world.getBlockState(block).isAir()) cacheBlocks.add(block);
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        debug("Block data obtained.");

        updating = false;
    }

    @Override
    public void onRenderPass(RenderEvent event)
    {
        if (!enabled) return;

        // Render xray
        PathRenderer.drawManySelectionBoxes(event.getModelViewStack(), Helper.mc.getRenderViewEntity(), cacheBlocks, Color.cyan);
    }

    @Override
    public void onBlockBreak(BlockPos pos)
    {
        cacheBlocks.removeIf(b -> b.equals(pos));
    }
}
