package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical exhaustion factors used by server-authoritative player movement. */
@MechanicType("player.exhaustion")
public interface MovementExhaustionBehavior extends VersionedMechanic {
    enum JumpKind {
        SPRINTING,
        NORMAL
    }

    enum MovementKind {
        SWIMMING,
        UNDERWATER_WALKING,
        WATER_WALKING,
        SPRINTING,
        SNEAKING,
        WALKING
    }

    default float jumpExhaustion(Player player, JumpKind kind, float vanilla) {
        return vanilla;
    }

    default float movementExhaustionFactor(Player player, MovementKind kind, float vanilla) {
        return vanilla;
    }
}
