package me.wolfii.legacyparkourcompat.mechanic.hook.push;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** Resolves the horizontal velocity written to one recipient by Entity.push(Entity). */
@MechanicType("player.push.normalization")
public interface PlayerPushNormalizationHook extends VersionedMechanic {
    Vec3 normalize(Entity receiver, Entity other);
}
