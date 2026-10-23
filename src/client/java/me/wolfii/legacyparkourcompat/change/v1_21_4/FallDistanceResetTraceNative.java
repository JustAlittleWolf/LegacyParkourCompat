package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.movement.FallDistanceResetTraceHook;
import net.minecraft.world.phys.Vec3;

/**
 * Bounds the full-trace operation to the independently accepted 1.21.5-1.21.8 group.
 * Earlier profiles retain their existing native endpoint; this is a resolution
 * boundary, not a claim that their source uses the capped endpoint.
 *
 * <p>Compared sources: Mojmap 1.21.5 and 1.21.8,
 * {@code net.minecraft.world.entity.Entity#move}, use the resolved movement;
 * Mojmap 1.21.9 and 1.21.10 and unobfuscated 26.2 {@code Entity#move}
 * normalize and cap it at eight blocks.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FallDistanceResetTraceNative implements FallDistanceResetTraceHook {
    @Override
    public Vec3 fallResetTraceDisplacement(Vec3 resolvedDisplacement, Vec3 nativeTraceDisplacement) {
        return nativeTraceDisplacement;
    }
}
