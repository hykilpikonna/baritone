package org.hydev.hyritone.utils;

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

    /**
     * Get what does it take to rotate to a direction
     *
     * @param vec Final direction
     * @return Rotation required
     * @author Wurst7 https://github.com/Wurst-Imperium/Wurst7
     */
    public static Rotation getNeededRotations(Vec3d vec)
    {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new Rotation(yaw, pitch);
    }

    /**
     * Rotate
     *
     * @param rotation Relative rotation
     */
    public static void rotate(Rotation rotation)
    {
        player().rotationYaw = rotation.getYaw();
        player().rotationPitch = rotation.getPitch();
    }

    /**
     * Rotate to face one block
     *
     * @param pos Block position
     * @return Direction
     * @author Wurst7 https://github.com/Wurst-Imperium/Wurst7
     */
    public static Direction rotateToBlock(BlockPos pos)
    {
        Direction side = null;
        Direction[] sides = Direction.values();

        Vec3d eyesPos = getEyesPos();
        Vec3d relCenter = world().getBlockState(pos).getShape(world(), pos).getBoundingBox().getCenter();
        Vec3d center = new Vec3d(pos).add(relCenter);

        Vec3d[] hitVecs = new Vec3d[sides.length];
        for (int i = 0; i < sides.length; i++)
        {
            Vec3i dirVec = sides[i].getDirectionVec();
            Vec3d relHitVec = new Vec3d(relCenter.x * dirVec.getX(),
                relCenter.y * dirVec.getY(), relCenter.z * dirVec.getZ());
            hitVecs[i] = center.add(relHitVec);
        }

        BlockState state = world().getBlockState(pos);
        for (int i = 0; i < sides.length; i++)
        {
            // check line of sight
            if (world().rayTraceBlocks(eyesPos, hitVecs[i], pos, state.getShape(world(), pos), state) != null)
            {
                continue;
            }

            side = sides[i];
            break;
        }

        if (side == null)
        {
            double distanceSqToCenter = eyesPos.squareDistanceTo(center);
            for (int i = 0; i < sides.length; i++)
            {
                // check if side is facing towards player
                if (eyesPos.squareDistanceTo(hitVecs[i]) >= distanceSqToCenter)
                {
                    continue;
                }

                side = sides[i];
                break;
            }
        }

        // player is inside of block, side doesn't matter
        if (side == null)
        {
            side = sides[0];
        }

        // Rotate
        rotate(getNeededRotations(hitVecs[side.ordinal()]));

        return side;
    }

    /**
     * Interact
     *
     * @param pos Block
     */
    public static void interact(BlockPos pos)
    {
        rotateToBlock(pos);
        mouse().rightClick();
    }

    /**
     * Dig the block at direction
     *
     * @param pos Block position
     * @param side Direction
     * @return Dig success or not
     * @author Wurst7 https://github.com/Wurst-Imperium/Wurst7
     */
    public static boolean dig(BlockPos pos, Direction side)
    {
        // damage block
        if (!controller().onPlayerDamageBlock(pos, side))
        {
            return false;
        }

        // swing arm
        player().connection.sendPacket(new CAnimateHandPacket(Hand.MAIN_HAND));

        return true;
    }

    /**
     * Dig the block at direction
     *
     * @param pos Block position
     * @return Dig success or not
     */
    public static boolean dig(BlockPos pos)
    {
        return dig(pos, rotateToBlock(pos));
    }

    /**
     * Delay between swapping items from inv to chest.
     *
     * @return Delay between 80 to 120
     */
    public static int chestDelay()
    {
        return (int) (80 + Math.random() * 40);
    }
}
