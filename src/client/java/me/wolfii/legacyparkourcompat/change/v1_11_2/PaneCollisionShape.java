package me.wolfii.legacyparkourcompat.change.v1_11_2;

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
 * Supplies the shared pane components using the 1.11.2 default-state cube
 * selector. Minecraft 1.11.2 (Ornithe Feather Gen2)
 * {@code PaneBlock.resolveVirtualProperties} and {@code shouldConnectTo(Block)}
 * use {@code defaultState().isCube()} followed by pane/glass exceptions; the
 * accepted policy preserves NATIVE for unknown current IDs and ambiguous slab
 * states. Component literals and emission order match Minecraft 1.12/1.12.1/1.12.2
 * (Ornithe Feather Gen2) {@code PaneBlock.SHAPES} and {@code addCollisions}.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
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
