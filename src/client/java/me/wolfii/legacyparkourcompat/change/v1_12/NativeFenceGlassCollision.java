package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fence.FenceGlassCollisionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Keeps the native collision query for profiles before the accepted 1.13.2
 * fence/glass comparison. Earlier neighbor-predicate behavior has not been
 * established, so this resolver entry prevents the 1.13 change from applying
 * backward while preserving native fallback for the unproved interval.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeFenceGlassCollision implements FenceGlassCollisionHook {
    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos, VoxelShape shape) {
        return Optional.empty();
    }
}
