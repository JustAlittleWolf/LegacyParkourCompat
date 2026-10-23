package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.LegacyFenceWallNeighborConnectionHook;
import net.minecraft.world.level.block.state.BlockState;

/** Keeps the native neighbor predicate through the last source-unverified profile before 1.12. */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class NativeFenceWallNeighborConnections implements LegacyFenceWallNeighborConnectionHook {
    public NativeFenceWallNeighborConnections() {
    }

    @Override
    public boolean restoresSolidNeighborConnection(BlockState neighbor) {
        return false;
    }
}
