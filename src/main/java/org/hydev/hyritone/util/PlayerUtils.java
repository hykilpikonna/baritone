package org.hydev.hyritone.util;

import baritone.api.utils.Helper;
import baritone.api.utils.Rotation;
import baritone.utils.accessor.IMouse;
import net.minecraft.block.BlockState;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.play.client.CAnimateHandPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

/**
 * Utils for controlling the player.
 * <p>
 * Class created by the HyDEV Team on 2020-01-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-01-24 22:34
 */
public class PlayerUtils
{
    public static ClientPlayerEntity player() { return Helper.mc.player; }
    public static ClientWorld world() { return Helper.mc.world; }
    public static IMouse mouse() { return (IMouse) Helper.mc.mouseHelper; }
    public static PlayerController controller() { return Helper.mc.playerController; }

    /**
     * Get player's eye vector
     *
     * @return Eye vector
     * @author Wurst7 https://github.com/Wurst-Imperium/Wurst7
     */
    public static Vec3d getEyesPos()
    {
        assert player() != null;

        return new Vec3d(player().getPosX(),
            player().getPosY() + player().getEyeHeight(player().getPose()),
            player().getPosZ());
    }
}
