package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.PaneCollisionComponents;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.pane.PaneNeighborEligibilityHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 1.12/1.12.1/1.12.2 PaneBlock.addCollisions emits the center post, then
 * north, south, east, and west arms selected by the historical FaceShape
 * predicate. The finite current-ID provider table is bound by the accepted
 * pane FaceShape/crosswalk audit. Unknown and modern-only neighbors retain the
 * native pane shape by returning empty.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class PaneCollisionShape implements BlockCollisionShapeHook {
    private final String blockId;

    @MovementChange.Factory
    public static List<PaneCollisionShape> variants() {
        return PaneCollisionComponents.BLOCK_IDS.stream().map(PaneCollisionShape::new).toList();
    }

    public PaneCollisionShape(String blockId) {
        this.blockId = Objects.requireNonNull(blockId, "blockId");
    }

    @Override
    public String blockId() {
        return blockId;
    }

    @Override
    public Optional<List<VoxelShape>> collisionShapes(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return MovementRuntime.find(PaneNeighborEligibilityHook.class, MovementRuntime.playerFrom(context))
            .flatMap(policy -> PaneCollisionComponents.selected(pos, (neighborPos, face) ->
                policy.connects(level.getBlockState(neighborPos), level, neighborPos, face)));
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return collisionShapes(state, level, pos, context).map(PaneCollisionComponents::union);
    }
}
