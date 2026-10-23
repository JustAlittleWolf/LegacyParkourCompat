package me.wolfii.legacyparkourcompat.mechanic.hook.fence;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Removes only the fence rail that historical collision excluded beside glass. */
@MechanicType("block.fence_glass_collision")
public interface FenceGlassCollisionHook extends VersionedMechanic {
    Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos, VoxelShape shape);
}
