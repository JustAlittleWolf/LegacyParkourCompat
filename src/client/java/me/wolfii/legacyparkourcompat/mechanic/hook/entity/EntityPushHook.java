package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Controls the local player's velocity writes during ordinary living-entity collision pushes. */
@MechanicType("entity.push_response")
public interface EntityPushHook extends VersionedMechanic {
    boolean suppressCollisionPush();
}
