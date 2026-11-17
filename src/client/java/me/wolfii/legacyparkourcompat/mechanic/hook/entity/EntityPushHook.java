package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Controls whether a push response is applied to its receiving entity. */
@MechanicType("entity.push_response")
public interface EntityPushHook extends VersionedMechanic {
    boolean suppressInboundPush(Entity receiver, Entity source);
}
