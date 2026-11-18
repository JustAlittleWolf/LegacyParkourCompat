package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Resolves the delta movement supplied to the replacement local player's respawn copy. */
@MechanicType("player.respawn.velocity")
public interface RespawnVelocityHook extends VersionedMechanic {
    Vec3 replacementVelocity(ResourceKey<Level> oldDimension, ResourceKey<Level> newDimension, Vec3 vanillaVelocity);
}
