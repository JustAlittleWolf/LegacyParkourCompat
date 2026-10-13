package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Inverse square root used to normalize the auto-jump collision probe. */
@FunctionalInterface
@MechanicType("player.auto_jump.inverse_sqrt")
public interface AutoJumpInverseSqrtHook extends VersionedMechanic {
    float inverseSqrt(Player player, float value, float vanilla);
}
