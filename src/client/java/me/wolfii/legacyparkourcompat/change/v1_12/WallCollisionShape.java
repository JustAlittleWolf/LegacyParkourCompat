package me.wolfii.legacyparkourcompat.change.v1_12;

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

/**
 * Restores the full old connection-indexed collision box for the two walls present in 1.12.
 *
 * <p>Ornithe Feather 1.12.2 {@code net.minecraft.block.WallBlock#getCollisionShape} selects
 * {@code COLLISION_SHAPES[getShapeIndex(state)]}; {@code getShapeIndex} reads only the four
 * horizontal connection properties and selects one of 16 boxes with maximum Y 24/16.
 * Ornithe Feather 1.13 and 1.13.2 {@code net.minecraft.block.WallBlock#getCollisionShape}
 * selects a component union for UP=true and inherited {@code PaneBlock#getCollisionShape}
 * for UP=false. This change applies only at the V1_12 boundary; earlier profiles retain the
 * separately resolved earlier-profile provider.
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
    public Optional<VoxelShape> horizontalCollisionShape(BlockState state, VoxelShape vanillaShape) {
        if (!(state.getBlock() instanceof WallBlock) || vanillaShape.isEmpty()) {
            return Optional.empty();
        }

        VoxelShape oldShape = WallCollisionGeometry.shape(WallCollisionGeometry.mask(state));
        return Optional.of(Shapes.box(
            oldShape.min(Direction.Axis.X), 0.0, oldShape.min(Direction.Axis.Z),
            oldShape.max(Direction.Axis.X), vanillaShape.max(Direction.Axis.Y), oldShape.max(Direction.Axis.Z)
        ));
    }

}
