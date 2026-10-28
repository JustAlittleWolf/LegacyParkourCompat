package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import java.util.Optional;

/** Historical lava-presence query, with an empty result preserving the native value. */
@MechanicType("entity.lava_presence")
public interface LavaPresenceHook extends VersionedMechanic {
    Optional<Boolean> isInLava(Entity entity, Optional<LavaMaterialScanUpperBoundHook> scanUpperBound);

    default void afterMove(Entity entity) {
    }
}
