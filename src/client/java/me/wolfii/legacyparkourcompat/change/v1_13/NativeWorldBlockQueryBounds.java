package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.BlockQueryBounds;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.WorldBlockQueryBoundsHook;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/** Keeps native query bounds before the source-backed 1.13.2 profile group. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class NativeWorldBlockQueryBounds implements WorldBlockQueryBoundsHook {
    @Override
    public Optional<BlockQueryBounds> bounds(AABB queryBox) {
        return Optional.empty();
    }
}
