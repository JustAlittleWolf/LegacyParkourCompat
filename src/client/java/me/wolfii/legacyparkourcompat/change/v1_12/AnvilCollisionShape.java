package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * Restores the one-piece anvil collision cuboid for profiles from 1.9 through
 * 1.12. Ornithe Feather 1.9 AnvilBlock#getShape selects SHAPE_X or SHAPE_Z
 * solely from the queried state; BlockState#getCollisionShape dispatches to
 * Block#getCollisionShape, which delegates to that state shape. The same local
 * boxes and per-state selection are present in 1.9.4, 1.10.2, 1.11.2 and
 * 1.12.2. In 1.8.9, AnvilBlock#updateShape instead writes shared mutable bounds
 * that the movement collision query reads without refreshing; a preceding
 * writer can therefore change the result, so the native fallback is retained
 * for that profile. In Ornithe Feather 1.13, AnvilBlock#getShape instead
 * selects the union of BASE_SHAPE, two legs and TOP_SHAPE, and
 * Block#getCollisionShape still delegates to it. AnvilBlock#getShape retains
 * that geometry in 1.13.2. The first inspected state-selected consumer is 1.9,
 * while the component geometry first appears in 1.13; neither comparison
 * locates an uninspected patch-level transition.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class AnvilCollisionShape implements BlockCollisionShapeHook {
    private static final List<String> ANVIL_BLOCKS = List.of(
        "minecraft:anvil",
        "minecraft:chipped_anvil",
        "minecraft:damaged_anvil"
    );

    private static final VoxelShape SHAPE_X = Shapes.box(0.0, 0.0, 0.125, 1.0, 1.0, 0.875);
    private static final VoxelShape SHAPE_Z = Shapes.box(0.125, 0.0, 0.0, 0.875, 1.0, 1.0);

    private final String blockId;

    private AnvilCollisionShape(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<AnvilCollisionShape> createVariants() {
        return ANVIL_BLOCKS.stream().map(AnvilCollisionShape::new).toList();
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        Direction facing = state.getValue(AnvilBlock.FACING);
        VoxelShape shape = facing.getAxis() == Direction.Axis.X ? SHAPE_X : SHAPE_Z;
        return Optional.of(shape);
    }
}
