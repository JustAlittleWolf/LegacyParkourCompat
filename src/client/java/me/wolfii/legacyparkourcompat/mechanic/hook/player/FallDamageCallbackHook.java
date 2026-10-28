package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Resolves whether Entity.move dispatches its fall-distance callback. */
@MechanicType("player.fall_damage_callback")
public interface FallDamageCallbackHook extends VersionedMechanic {
    boolean shouldCall(Entity entity);
}
