package me.wolfii.legacyparkourcompat.change.v1_21_5.block;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.EntityInsideShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Restores the 1.21.5 portal outline for swept entity contact. */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class NetherPortalEntityInsideShape implements EntityInsideShapeHook {
    @Override
    public String variant() {
        return "minecraft:nether_portal";
    }

    @Override
    public Optional<VoxelShape> entityInsideShape(
        BlockBehaviour.BlockStateBase state, BlockGetter level, BlockPos pos, Entity entity
    ) {
        // Mojmap 1.21.5 NetherPortalBlock#getEntityInsideCollisionShape(BlockState, BlockGetter,
        // BlockPos, Entity) returns state.getShape(level, pos). Mojmap 1.21.4 and 1.21.6 omit
        // that NetherPortalBlock override, so BlockBehaviour#getEntityInsideCollisionShape returns
        // Shapes.block(). Unobfuscated 26.2 NetherPortalBlock#getShape(BlockState, BlockGetter,
        // BlockPos, CollisionContext) retains the same axis-specific SHAPES lookup, while
        // BlockBehaviour.BlockStateBase#getShape(BlockGetter, BlockPos) delegates to that provider.
        // Its getEntityInsideCollisionShape method still delegates to the native block provider;
        // returning this outline only for the selected 1.21.5 player profile restores its override.
        return Optional.of(state.getShape(level, pos));
    }
}
