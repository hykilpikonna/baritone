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

import baritone.utils.accessor.IMouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.hydev.hyritone.util.MiscUtils.sleep;

/**
 * Mixin to control mouse operations
 * <p>
 * Class created by the HyDEV Team on 2020-02-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-02-21 13:54
 */
@Mixin(MouseHelper.class)
public abstract class MixinMouse implements IMouse
{
    @Shadow
    protected abstract void mouseButtonCallback(long window, int button, int action, int mods);

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "mouseButtonCallback", at = @At("HEAD"), cancellable = true)
    private void inject(long window, int button, int action, int mods, CallbackInfo info)
    {
        System.out.println(window + " | " + button + " | " + action + " | " + mods + " | " + System.currentTimeMillis());
    }

    public void rightClick()
    {
        new Thread(() ->
        {
            rightHold();
            sleep((long) (70 + Math.random() * 10));
            rightRelease();
        }).start();
    }

    public void leftClick()
    {
        new Thread(() ->
        {
            leftHold();
            sleep((long) (70 + Math.random() * 10));
            leftRelease();
        }).start();
    }

    @Override
    public void rightHold()
    {
        mouseButtonCallback(minecraft.getMainWindow().getHandle(), 1, 1, 0);
    }

    @Override
    public void rightRelease()
    {
        mouseButtonCallback(minecraft.getMainWindow().getHandle(), 1, 0, 0);
    }

    @Override
    public void leftHold()
    {
        mouseButtonCallback(minecraft.getMainWindow().getHandle(), 0, 1, 0);
    }

    @Override
    public void leftRelease()
    {
        mouseButtonCallback(minecraft.getMainWindow().getHandle(), 0, 0, 0);
    }
}
