package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.StoredKeyboardInputHook;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import static me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport.impulse;

/**
 * Emulates 1.18.2 through 1.21.3 under the default-scalar applicability guard.
 * Mojmap 1.18.2 KeyboardInput.tick uses constant float 0.3F; from 1.19
 * through 1.21.3 LocalPlayer.aiStep supplies the ordinary
 * default sneak scalar (0.3F) to KeyboardInput.tick(boolean, float), which
 * scales each independent axis before LocalPlayer applies the item-use
 * multiplier afterward. The consumer supplies exactly 0.3F, so the constant
 * and supplied-float operations are identical within this scope. Custom
 * scalar production is outside this hook.
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
