package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

/** Resolves additional historical fence and wall connections from their local neighbors. */
@MechanicType("block.fence_wall_neighbor_connection")
public interface LegacyFenceWallNeighborConnectionHook extends VersionedMechanic {
    int NORTH_MASK = 1 << 2;
    int EAST_MASK = 1 << 3;
    int SOUTH_MASK = 1;
    int WEST_MASK = 1 << 1;

    boolean restoresSolidNeighborConnection(BlockState neighbor);

    default int restoredConnectionMask(BlockGetter level, BlockPos pos) {
        int mask = 0;
        if (restoresSolidNeighborConnection(level.getBlockState(pos.north()))) mask |= NORTH_MASK;
        if (restoresSolidNeighborConnection(level.getBlockState(pos.east()))) mask |= EAST_MASK;
        if (restoresSolidNeighborConnection(level.getBlockState(pos.south()))) mask |= SOUTH_MASK;
        if (restoresSolidNeighborConnection(level.getBlockState(pos.west()))) mask |= WEST_MASK;
        return mask;
    }
}
