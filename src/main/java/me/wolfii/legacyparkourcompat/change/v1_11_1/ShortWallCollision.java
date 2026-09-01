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
 * 1.11.1 only (MC-111645): {@code WallBlock#addCollisions} used the 1.0 / 0.875
 * outline table instead of the 1.5 collision table. 1.11.2 restored 1.5b.
 */
@MovementChange(emulates = ParkourVersion.V1_11_1)
public final class ShortWallCollision implements BlockCollisionShape {
    private final String blockId;

    public ShortWallCollision(String blockId) {
        this.blockId = blockId;
    }

    public static void register(MovementChangeRegistry registry) {
        BlockChanges.registerEach(registry, WallBlock.class::isInstance, ShortWallCollision::new);
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
        return Optional.of(WallBoxes.outline(state));
    }
}
