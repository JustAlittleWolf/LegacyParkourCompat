package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.StoredKeyboardInputHook;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import static me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport.impulse;

/**
 * Emulates 1.18.2 through 1.21.3 under the vanilla-feature applicability guard.
 * Mojmap 1.18.2 KeyboardInput.tick uses constant float 0.3F; from 1.19
 * through 1.21.3 LocalPlayer.aiStep supplies the ordinary
 * sneak scalar to KeyboardInput.tick(boolean, float), which
 * scales each independent axis before LocalPlayer applies the item-use
 * multiplier afterward. The independently resolved SneakSpeedScalarHook
 * supplies the fixed 1.18.2 value or later enchantment/attribute result.
 * Custom modern scalar production retains native fallback.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class StoredKeyboardInput implements StoredKeyboardInputHook {
    @Override
    public Vec2 apply(Input keys, boolean slow, float sneakingSpeed, boolean itemUseSlowdown) {
        float left = impulse(keys.left(), keys.right());
        float forward = impulse(keys.forward(), keys.backward());
        if (slow) {
            left *= sneakingSpeed;
            forward *= sneakingSpeed;
        }
        if (itemUseSlowdown) {
            left *= 0.2F;
            forward *= 0.2F;
        }
        return new Vec2(left, forward);
    }
}
