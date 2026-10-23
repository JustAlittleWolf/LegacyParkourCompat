package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.VelocityZeroThresholdHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Restores the component-wise player cutoff used before the horizontal-vector
 * cutoff was introduced.
 *
 * <p>In Mojmap 1.21.4, {@code LivingEntity#aiStep} independently replaces each
 * component whose absolute value is strictly below {@code 0.003} with positive
 * zero. Mojmap 1.21.5 changes the player X/Z gate in the same method to compare
 * horizontal squared length against {@code 9.0E-6}; the Y component remains
 * component-wise. Keep this hook focused on the historical component operation
 * so the caller can apply it only to the components selected by its source gate.
 * In unobfuscated 26.2, {@code LivingEntity#aiStep} first copies the movement
 * components, applies that radial gate for players (and component gates for
 * other entities), applies the Y gate, then calls {@code setDeltaMovement(DDD)}.
 * The existing three argument hooks at that call restore the 1.21.4 player
 * component gate after the native radial check; no additional deadzone hook or
 * mixin operation is needed.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PlayerVelocityZeroThreshold implements VelocityZeroThresholdHook {
    @Override
    public double filterComponent(LivingEntity entity, double component) {
        return Math.abs(component) < 0.003 ? 0.0 : component;
    }
}
