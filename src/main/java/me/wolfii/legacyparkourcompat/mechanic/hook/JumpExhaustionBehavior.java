package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Exhaustion charged for one ground jump. */
@FunctionalInterface
@MechanicType("player.exhaustion.jump")
public interface JumpExhaustionBehavior extends VersionedMechanic {
    enum JumpKind {
        SPRINTING,
        NORMAL
    }

    float jumpExhaustion(Player player, JumpKind kind, float vanilla);
}
