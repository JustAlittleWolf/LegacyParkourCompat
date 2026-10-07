package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether the ground-jump call selected by aiStep should run. */
@FunctionalInterface
@MechanicType("player.jump.ground_gate")
public interface GroundJumpGateBehavior extends VersionedMechanic {
    boolean shouldJumpFromGround(Player player, boolean vanilla);
}
