package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.PaneCollisionComponents;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockMovementCollisionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Native fence for the grouped 1.13 collector range, whose pane collector parity is not implemented here. */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class PaneCollisionNative implements BlockCollisionShapeHook, BlockMovementCollisionHook {
    private final String blockId;

    @MovementChange.Factory
    public static List<PaneCollisionNative> variants() {
        return PaneCollisionComponents.BLOCK_IDS.stream().map(PaneCollisionNative::new).toList();
    }

    public PaneCollisionNative(String blockId) {
        this.blockId = Objects.requireNonNull(blockId, "blockId");
    }

    @Override public String blockId() { return blockId; }
    @Override public String variant() { return blockId; }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                                CollisionContext context) {
        return Optional.empty();
    }

    @Override
    public VoxelShape collect(Entity player, BlockPos pos, AABB query, VoxelShape nativeShape,
                              List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip) {
        return nativeShape;
    }
}
