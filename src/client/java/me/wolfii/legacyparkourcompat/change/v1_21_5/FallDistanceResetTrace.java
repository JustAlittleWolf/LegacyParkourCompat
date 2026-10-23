package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.movement.FallDistanceResetTraceHook;
import net.minecraft.world.phys.Vec3;

/**
 * Restores the complete resolved movement as the fall-distance reset trace.
 *
 * <p>Mojmap 1.21.5, 1.21.6, 1.21.7 and 1.21.8,
 * {@code net.minecraft.world.entity.Entity#move}, clip from {@code position()}
 * to {@code position().add(resolvedMovement)}. Mojmap 1.21.9 and 1.21.10,
 * {@code Entity#move}, instead normalize that movement and cap the trace at
 * eight blocks. The 26.2 unobfuscated {@code Entity#move} retains the capped
 * expression this hook replaces for the 1.21.5-1.21.8 group. The vanilla
 * fall-distance gate, movement length threshold, clip modes, hit test, and reset
 * call remain in the native method.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class FallDistanceResetTrace implements FallDistanceResetTraceHook {
    @Override
    public Vec3 fallResetTraceDisplacement(Vec3 resolvedDisplacement, Vec3 nativeTraceDisplacement) {
        return resolvedDisplacement;
    }
}
