package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.Optional;

/**
 * Omits the sole Beacon-side arm from the ordinary dry cobblestone wall fixture.
 *
 * <p>In 1.13–1.13.2 Ornithe Feather, {@code WallBlock.shouldConnectTo} uses
 * {@code Block.isExceptionForAttachment}, which includes Beacon through
 * {@code Block.isExceptionForPlacement}. {@code WallBlock.getCollisionShape}
 * and {@code PaneBlock.makeShapes} give the remaining centered post.
 * In 1.14 Ornithe Feather, {@code WallBlock.getPlacementState} tests the
 * full collision face and {@code Block.isExceptionForAttachment} no longer
 * excludes Beacon. In 26.2 unobfuscated, {@code WallBlock.getStateForPlacement},
 * {@code WallBlock.shouldRaisePost} and {@code WallBlock.makeShapes} give a
 * raised post and one LOW arm with the same 24/16 collision height.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class BeaconWallCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape POST = Block.box(4.0, 0.0, 4.0, 12.0, 24.0, 12.0);
    private static final Map<Direction, VoxelShape> ONE_ARM_SHAPES =
        Shapes.rotateHorizontal(Shapes.or(POST, Block.box(5.0, 0.0, 0.0, 11.0, 24.0, 11.0)));

    @Override
    public String blockId() {
        return "minecraft:cobblestone_wall";
    }

    @Override
    public boolean supports(ParkourVersion selected) {
        return selected == ParkourVersion.V1_13 || selected == ParkourVersion.V1_13_1;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return Optional.empty();
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        int restoredConnectionMask,
        VoxelShape vanillaShape
    ) {
        if (!(MovementRuntime.playerFrom(context) instanceof LocalPlayer)
            || context.isPlacement() || context.alwaysCollideWithFluid()
            || !(level instanceof CollisionGetter collisionGetter)
            || !state.is(Blocks.COBBLESTONE_WALL) || state.getValue(WallBlock.WATERLOGGED)
            || !state.getValue(WallBlock.UP)) {
            return Optional.empty();
        }

        BlockPos above = pos.above();
        BlockGetter wallChunk = collisionGetter.getChunkForCollisions(above.getX() >> 4, above.getZ() >> 4);
        if (wallChunk == null || !wallChunk.getBlockState(above).is(Blocks.AIR)) {
            return Optional.empty();
        }

        Direction beaconSide = null;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighborPos = pos.relative(direction);
            BlockGetter loadedChunk = collisionGetter.getChunkForCollisions(
                neighborPos.getX() >> 4, neighborPos.getZ() >> 4
            );
            if (loadedChunk == null) {
                return Optional.empty();
            }

            WallSide side = state.getValue(WallBlock.PROPERTY_BY_DIRECTION.get(direction));
            BlockState neighbor = loadedChunk.getBlockState(neighborPos);
            if (side == WallSide.LOW && beaconSide == null && neighbor.is(Blocks.BEACON)) {
                beaconSide = direction;
            } else if (side != WallSide.NONE || !neighbor.is(Blocks.AIR)) {
                return Optional.empty();
            }
        }
        if (beaconSide == null || Shapes.joinIsNotEmpty(vanillaShape, ONE_ARM_SHAPES.get(beaconSide), BooleanOp.NOT_SAME)) {
            return Optional.empty();
        }
        return Optional.of(POST);
    }
}
