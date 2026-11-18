package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/** Resolves the delta movement supplied to the replacement local player's respawn copy. */
@MechanicType("player.respawn.velocity")
public interface RespawnVelocityHook extends VersionedMechanic {
    Vec3 replacementVelocity(Vec3 vanillaVelocity);
}
