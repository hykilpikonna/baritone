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

package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.utils.accessor.IPlayerControllerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerController.class)
public abstract class MixinPlayerController implements IPlayerControllerMP {

    @Shadow
    @Final
    private Minecraft mc;

    @Accessor
    @Override
    public abstract void setIsHittingBlock(boolean isHittingBlock);

    @Accessor
    @Override
    public abstract BlockPos getCurrentBlock();

    @Invoker
    @Override
    public abstract void callSyncCurrentPlayItem();

    @Inject(
        method = "onPlayerDestroyBlock",
        at = @At(value = "TAIL")
    )
    public void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        // Check if the block is broken
        if (!cir.getReturnValue()) return;

        // Call event
        IBaritone baritone = BaritoneAPI.getProvider().getBaritoneForPlayer(this.mc.player);
        if (baritone != null) {
            baritone.getGameEventHandler().onBlockBreak(pos);
        }
    }
}
