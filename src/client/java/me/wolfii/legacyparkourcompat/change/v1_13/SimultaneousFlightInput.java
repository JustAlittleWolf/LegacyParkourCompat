package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SimultaneousFlightInputHook;
import me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Ornithe Feather 1.8.9 and 1.13.2 LocalClientPlayerEntity.mobTick, under
 * flying && isCamera, subtract getFlySpeed()*3.0F on sneak, then add the
 * same float product on jump, through two double velocityY assignments.
 * Ornithe Feather 1.14 mobTick sums integer input first; 26.2 unobfuscated
 * LocalPlayer.aiStep retains that cancellation and skips both-held writes.
 * Restore the two old writes only when both keyboard keys are held; the
 * native single-key arithmetic is unchanged. The caller supplies controlled
 * camera admission. Unsupported modern-only input/pose/scale states remain native.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class SimultaneousFlightInput implements SimultaneousFlightInputHook {
    @Override
    public void apply(Player player, ParkourVersion selected, Input keys) {
        if (!player.getAbilities().flying || !keys.shift() || !keys.jump()
            || !KeyboardInputSupport.supportsMovementFeatures(player, selected)) {
            return;
        }
        Vec3 movement = player.getDeltaMovement();
        player.setDeltaMovement(movement.x, movement.y - player.getAbilities().getFlyingSpeed() * 3.0F, movement.z);
        movement = player.getDeltaMovement();
        player.setDeltaMovement(movement.x, movement.y + player.getAbilities().getFlyingSpeed() * 3.0F, movement.z);
    }
}
