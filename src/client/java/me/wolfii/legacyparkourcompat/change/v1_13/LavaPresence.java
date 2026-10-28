package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.lava.LavaMaterialQuery;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaPresenceHook;
import net.minecraft.world.entity.Entity;
import java.util.Optional;

/**
 * The immediate contracted-box query is common from 1.8.9 through 1.13.2.
 * Sources: Ornithe Feather Gen2 build 2, {@code Entity.isInLava} and
 * {@code World.containsMaterial} in 1.8.9 and 1.13.2. Both releases contract
 * with the same float literals and scan x, then y, then z, returning at the
 * first matching lava material.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class LavaPresence implements LavaPresenceHook {
    public LavaPresence() {
    }

    @Override
    public Optional<Boolean> isInLava(Entity entity, Optional<LavaMaterialScanUpperBoundHook> scanUpperBound) {
        LavaMaterialScanUpperBoundHook bound = scanUpperBound.orElseThrow(
            () -> new IllegalStateException("Historical lava scan bound is missing")
        );
        return Optional.of(LavaMaterialQuery.isInLava(entity, bound));
    }
}
