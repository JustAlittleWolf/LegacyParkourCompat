package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Historical {@code handleRelativeFrictionAndCalculateMovement} (ground accel + climb).
 */
@MechanicType("player.friction_movement")
public interface FrictionMovementBehavior extends VersionedMechanic {
    Vec3 handleRelativeFrictionAndCalculateMovement(
        LivingEntity entity,
        Vec3 input,
        float friction,
        VanillaFn<Vec3> vanilla
    );
}
