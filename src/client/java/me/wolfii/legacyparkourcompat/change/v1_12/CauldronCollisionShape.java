package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * Ornithe Feather 1.8.9 {@code CauldronBlock.addCollisions} writes and tests
 * the base, west, north, east and south boxes in that order, then resets its
 * bounds. Feather 1.9.4, 1.10.2, 1.11.2 and 1.12.2
 * {@code CauldronBlock.addCollisions} retain the same bounds and order.
 * Feather 1.13 and 1.13.2 {@code CauldronBlock.getShape} instead subtract the
 * upper interior from the full block. Preserve the old five component bounds
 * for empty and water cauldrons; level does not select collision geometry.
 * The native outline remains unchanged. The 1.8.0–1.8.8 range is not
 * established by this comparison and retains native behavior.
 * Union decomposition and internal collision planes can differ from the old
 * collector's five separate boxes, so this does not claim strict clipping parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class CauldronCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape SHAPE = Shapes.or(
        Shapes.box(0.0, 0.0, 0.0, 1.0, 5.0 / 16.0, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 2.0 / 16.0, 1.0, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 2.0 / 16.0),
        Shapes.box(14.0 / 16.0, 0.0, 0.0, 1.0, 1.0, 1.0),
        Shapes.box(0.0, 0.0, 14.0 / 16.0, 1.0, 1.0, 1.0)
    );

    private final String blockId;

    private CauldronCollisionShape(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<CauldronCollisionShape> variants() {
        return List.of(
            new CauldronCollisionShape("minecraft:cauldron"),
            new CauldronCollisionShape("minecraft:water_cauldron")
        );
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return Optional.of(SHAPE);
    }
}
