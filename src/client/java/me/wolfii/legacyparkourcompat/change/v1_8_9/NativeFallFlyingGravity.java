package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.TravelFallFlyingGravityHook;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.DoubleSupplier;

/**
 * Ornithe Feather 1.8.9 LivingEntity.moveRelative has no Elytra branch.
 * Ornithe Feather 1.9 LivingEntity.moveRelative adds active fall-flying movement.
 * For pre-Elytra profiles use 26.2 unobfuscated LivingEntity.updateFallFlyingMovement's
 * getEffectiveGravity call, including the native Entity.getGravity result.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class NativeFallFlyingGravity implements TravelFallFlyingGravityHook {
    @Override
    public double gravity(LivingEntity entity, DoubleSupplier nativeGravity) {
        return nativeGravity.getAsDouble();
    }
}
