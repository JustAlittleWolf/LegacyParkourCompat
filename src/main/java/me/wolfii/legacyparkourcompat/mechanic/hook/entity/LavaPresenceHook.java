package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Historical immediate lava-presence query used by player movement. */
@MechanicType("entity.lava_presence")
public interface LavaPresenceHook extends VersionedMechanic {
    boolean isInLava(Entity entity, LavaMaterialScanUpperBoundHook scanUpperBound);
}
