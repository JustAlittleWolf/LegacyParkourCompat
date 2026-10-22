package me.wolfii.legacyparkourcompat.change.v1_12_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Restores the proven 1.12.2 horizontal wall boxes while keeping the vertical range
 * supplied by the independently resolved collision-height behavior.
 *
 * <p>Ornithe Feather 1.12.2 {@code net.minecraft.block.WallBlock#getCollisionShape}
 * selects {@code COLLISION_SHAPES[getShapeIndex(state)]}; its NORTH-only entry is
 * {@code x=.25..75,z=0..75}, and its NORTH/EAST/SOUTH entry is {@code x=.25..1,z=0..1}.
 * Ornithe Feather 1.13.2 {@code net.minecraft.block.WallBlock#getCollisionShape} selects
 * the union made by {@code PaneBlock#makeShapes} and {@code PaneBlock#getShapeIndex},
 * leaving these horizontal gaps. The geometry change begins at 1.13; 1.13.1 and 1.13.2
 * share it. This change only covers those two proven connection masks.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WallCollisionShape implements WallCollisionShapeHook {
    private static final VoxelShape NORTH_ONLY = Shapes.box(0.25, 0.0, 0.0, 0.75, 1.0, 0.75);
    private static final VoxelShape NORTH_EAST_SOUTH = Shapes.box(0.25, 0.0, 0.0, 1.0, 1.0, 1.0);

    @Override
    public Optional<VoxelShape> horizontalCollisionShape(BlockState state, VoxelShape vanillaShape) {
        if (!(state.getBlock() instanceof WallBlock) || vanillaShape.isEmpty()) {
            return Optional.empty();
        }

        WallSide north = state.getValue(WallBlock.NORTH);
        WallSide east = state.getValue(WallBlock.EAST);
        WallSide south = state.getValue(WallBlock.SOUTH);
        WallSide west = state.getValue(WallBlock.WEST);
        VoxelShape horizontal = isNorthOnly(north, east, south, west)
            ? NORTH_ONLY
            : isNorthEastSouth(north, east, south, west) ? NORTH_EAST_SOUTH : null;
        if (horizontal == null) {
            return Optional.empty();
        }

        return Optional.of(Shapes.box(
            horizontal.min(Direction.Axis.X), vanillaShape.min(Direction.Axis.Y), horizontal.min(Direction.Axis.Z),
            horizontal.max(Direction.Axis.X), vanillaShape.max(Direction.Axis.Y), horizontal.max(Direction.Axis.Z)
        ));
    }

    private static boolean isNorthOnly(WallSide north, WallSide east, WallSide south, WallSide west) {
        return north != WallSide.NONE && east == WallSide.NONE && south == WallSide.NONE && west == WallSide.NONE;
    }

    private static boolean isNorthEastSouth(WallSide north, WallSide east, WallSide south, WallSide west) {
        return north != WallSide.NONE && east != WallSide.NONE && south != WallSide.NONE && west == WallSide.NONE;
    }
}
