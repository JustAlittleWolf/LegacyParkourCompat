package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.StoredKeyboardInputHook;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import static me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport.impulse;

/**
 * In 1.21.4, LocalPlayer.aiStep moves item-use scaling before the sneak-speed
 * multiplication. KeyboardInput.tick still reconstructs independent signed
 * axes, and the pre-poll slow predicate remains in LocalPlayer.aiStep.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class StoredKeyboardInput implements StoredKeyboardInputHook {
    @Override
    public Vec2 apply(Input keys, boolean slow, float sneakingSpeed, boolean itemUseSlowdown) {
        float left = impulse(keys.left(), keys.right());
        float forward = impulse(keys.forward(), keys.backward());
        if (itemUseSlowdown) {
            left *= 0.2F;
            forward *= 0.2F;
        }
        if (slow) {
            left *= sneakingSpeed;
            forward *= sneakingSpeed;
        }
        return new Vec2(left, forward);
    }
}
