package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.WallCollisionGeometry;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Restores the full old connection-indexed collision box for the two walls present from
 * 1.8.9 through 1.12.2.
 *
 * <p>Ornithe Feather 1.8.9 {@code net.minecraft.block.WallBlock#getCollisionShape} computes
 * the same four-direction connection mask and 16 horizontal boxes as the later releases.
 * The normalized collision arrays are identical in 1.9.4, 1.10.2, 1.11.2 and 1.12.2;
 * 1.12.2 {@code WallBlock#getShapeIndex} reads NORTH/EAST/SOUTH/WEST only and selects a
 * {@code COLLISION_SHAPES} entry with maximum Y 24/16. The 1.12.2 neighbor predicate change
 * remains a separate operation. Ornithe Feather 1.13 and 1.13.2
 * {@code WallBlock#getCollisionShape} selects a component union for UP=true and inherited
 * {@code PaneBlock#getCollisionShape} for UP=false. This V1_12 change resolves for profiles
 * 1.8.9 through 1.12; V1_8 retains the narrower independently accepted two-mask provider.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WallCollisionShape implements WallCollisionShapeHook {
    private final String blockId;

    private WallCollisionShape(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<WallCollisionShape> variants() {
        return List.of(new WallCollisionShape("minecraft:cobblestone_wall"),
            new WallCollisionShape("minecraft:mossy_cobblestone_wall"));
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> horizontalCollisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        VoxelShape vanillaShape,
        OptionalDouble historicalMaxY
    ) {
        if (!(state.getBlock() instanceof WallBlock)) {
            return Optional.empty();
        }

        if (historicalMaxY.isEmpty()) {
            throw new IllegalStateException("Historical wall collision height is unresolved at V1_12");
        }
        VoxelShape oldShape = WallCollisionGeometry.shape(WallCollisionGeometry.mask(state));
        return Optional.of(Shapes.box(
            oldShape.min(Direction.Axis.X), 0.0, oldShape.min(Direction.Axis.Z),
            oldShape.max(Direction.Axis.X), historicalMaxY.getAsDouble(), oldShape.max(Direction.Axis.Z)
        ));
    }
}
