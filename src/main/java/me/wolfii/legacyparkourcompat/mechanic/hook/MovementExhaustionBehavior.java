package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Exhaustion factor per distance traveled. */
@FunctionalInterface
@MechanicType("player.exhaustion.movement")
public interface MovementExhaustionBehavior extends VersionedMechanic {
    enum MovementKind {
        SWIMMING,
        UNDERWATER_WALKING,
        WATER_WALKING,
        SPRINTING,
        SNEAKING,
        WALKING
    }

    float movementExhaustionFactor(Player player, MovementKind kind, float vanilla);
}
