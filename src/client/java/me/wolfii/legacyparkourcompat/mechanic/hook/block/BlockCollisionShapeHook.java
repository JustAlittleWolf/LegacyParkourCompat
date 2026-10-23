package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.List;

/**
 * Collision shape for one block. An empty result preserves the vanilla shape query.
 * The block identifier is the mechanic variant, so shapes resolve independently per block.
 */
@MechanicType("block.collision")
public interface BlockCollisionShapeHook extends VersionedMechanic {
    String blockId();

    /** Additional feature/applicability boundary without changing version resolution. */
    default boolean supports(ParkourVersion selected) {
        return true;
    }

    @Override
    default String variant() {
        return this.blockId();
    }

    /** Ordered provider parts for collectors; ordinary shape consumers use the union below. */
    default Optional<List<VoxelShape>> collisionShapes(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return this.collisionShape(state, level, pos, context).map(List::of);
    }

    Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    );
}
