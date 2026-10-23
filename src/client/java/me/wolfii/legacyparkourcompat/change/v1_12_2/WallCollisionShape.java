package me.wolfii.legacyparkourcompat.change.v1_12_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.WallCollisionGeometry;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Preserves the independently accepted two-mask coverage for 1.8 through 1.8.8.
 * The V1_8_9 change supplies the full table from the earliest source-proved profile.
 *
 * <p>Ornithe Feather 1.12.2 {@code net.minecraft.block.WallBlock#getCollisionShape} and
 * {@code getShapeIndex} use the same two horizontal extents represented here. In 1.13
 * and 1.13.2 the component shape is selected by wall UP state and pane connection mask.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
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
        BlockState state, VoxelShape vanillaShape, OptionalDouble historicalMaxY
    ) {
        if (!(state.getBlock() instanceof WallBlock) || vanillaShape.isEmpty()) {
            return Optional.empty();
        }

        int mask = WallCollisionGeometry.mask(state);
        if (mask != (1 << 2) && mask != ((1 << 2) | (1 << 3) | 1)) {
            return Optional.empty();
        }

        VoxelShape horizontal = WallCollisionGeometry.shape(mask);
        if (historicalMaxY.isEmpty()) {
            throw new IllegalStateException("Wall height is unresolved for a nonempty historical wall shape");
        }
        return Optional.of(Shapes.box(horizontal.min(Direction.Axis.X), 0.0, horizontal.min(Direction.Axis.Z),
            horizontal.max(Direction.Axis.X), historicalMaxY.getAsDouble(), horizontal.max(Direction.Axis.Z)));
    }
}
