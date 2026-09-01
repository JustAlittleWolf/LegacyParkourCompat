package me.wolfii.legacyparkourcompat.change.v1_11_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.BlockChanges;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.8–1.12 entity collision is the old 1.5-tall AABB table. 1.13 rebuilt wall
 * voxels. Registered as both {@link ParkourVersion#V1_11} and
 * {@link ParkourVersion#V1_12} so 1.11.1's short walls do not leak into 1.8–1.11
 * or 1.11.2–1.12.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class TallWallCollision implements BlockCollisionShape {
    private final String blockId;

    public TallWallCollision(String blockId) {
        this.blockId = blockId;
    }

    public static void register(MovementChangeRegistry registry) {
        BlockChanges.registerEach(registry, WallBlock.class::isInstance, id -> {
            TallWallCollision tall = new TallWallCollision(id);
            registry.register(BlockCollisionShape.class, ParkourVersion.V1_11, tall);
            return tall;
        });
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(WallBoxes.tallCollision(state));
    }

    @Override
    public Optional<VoxelShape> outlineShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(WallBoxes.outline(state));
    }
}
