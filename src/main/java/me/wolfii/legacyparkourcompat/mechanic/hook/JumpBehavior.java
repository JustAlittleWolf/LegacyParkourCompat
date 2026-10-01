package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/**
 * Historical jump ({@code LivingEntity#jumpFromGround}).
 */
@MechanicType("player.jump")
public interface JumpBehavior extends VersionedMechanic {
    default void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        vanilla.run();
    }

    default double jumpPower(LivingEntity entity, double vanilla) {
        return vanilla;
    }

    default double jumpVerticalVelocity(
        LivingEntity entity,
        double jumpPower,
        double currentVerticalVelocity,
        double vanilla
    ) {
        return vanilla;
    }
}
