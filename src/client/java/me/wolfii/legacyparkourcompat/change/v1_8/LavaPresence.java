package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.lava.LavaMaterialQuery;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaPresenceHook;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

/**
 * Immediate contracted-box lava lookup. Sources: Ornithe Feather Gen2 build 2,
 * {@code Entity.isInLava} and {@code World.containsMaterial} in 1.8.9 and 1.13.2.
 * Both releases contract with the same float literals and scan x, then y, then z,
 * returning at the first matching lava material.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class LavaPresence implements LavaPresenceHook, LavaMaterialScanUpperBoundHook {
    public LavaPresence() {
    }

    @Override
    public boolean isInLava(Entity entity, LavaMaterialScanUpperBoundHook scanUpperBound) {
        return LavaMaterialQuery.isInLava(entity, scanUpperBound);
    }

    @Override
    public int exclusiveUpperBound(double maximum) {
        return Mth.floor(maximum + 1.0);
    }
}
