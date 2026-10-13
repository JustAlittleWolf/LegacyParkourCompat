package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.StoredKeyboardInputHook;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import static me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport.impulse;

/**
 * Emulates 1.16 through 1.18.1, the last profiles using the double literal:
 * keyboard input narrows each axis after multiplying by
 * the double literal 0.3; item-use slowdown follows the sneak multiplication
 * through 1.21.3. The pre-poll boolean and independent key axes come from
 * LocalPlayer.aiStep and KeyboardInput.tick in 1.16 and 1.21.3 Mojmap.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class StoredKeyboardInput implements StoredKeyboardInputHook {
    @Override
    public Vec2 apply(Input keys, boolean slow, float sneakingSpeed, boolean itemUseSlowdown) {
        float left = impulse(keys.left(), keys.right());
        float forward = impulse(keys.forward(), keys.backward());
        if (slow) {
            left = (float) (left * 0.3);
            forward = (float) (forward * 0.3);
        }
        if (itemUseSlowdown) {
            left *= 0.2F;
            forward *= 0.2F;
        }
        return new Vec2(left, forward);
    }
}
