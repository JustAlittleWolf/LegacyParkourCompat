package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.LegacyFenceWallNeighborConnectionHook;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Restores the 1.12 solid-face connection for frosted ice and TNT.
 * Minecraft 1.12.2 (Ornithe Feather) FenceBlock.shouldConnectTo and
 * WallBlock.shouldConnectTo permit both blocks through their connection exceptions;
 * Minecraft 1.13 (Ornithe Feather) FenceBlock.shouldConnectTo and
 * WallBlock.isExceptionForConnection explicitly exclude them.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class FenceWallNeighborConnections implements LegacyFenceWallNeighborConnectionHook {
    public FenceWallNeighborConnections() {
    }

    @Override
    public boolean restoresSolidNeighborConnection(BlockState neighbor) {
        return neighbor.is(Blocks.FROSTED_ICE) || neighbor.is(Blocks.TNT);
    }
}
