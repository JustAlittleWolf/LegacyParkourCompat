package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.Optional;

/**
 * Omits the Soul Sand-side rail for the 1.14–1.15.2 collision rule in
 * the audited loaded-neighbor fixture.
 *
 * <p>In 1.14 Ornithe Feather, {@code FenceBlock.getPlacementState} and
 * {@code FenceBlock.updateShape} use {@code Block.isFaceSolid}; the 16×14×16
 * {@code SoulSandBlock.getCollisionShape} therefore leaves the horizontal arm
 * clear. In 1.15.2 Mojmap, {@code FenceBlock.getStateForPlacement} and
 * {@code FenceBlock.updateShape} use {@code Block.isFaceSturdy} against the
 * same-height {@code SoulsandBlock.getCollisionShape}, with the same result.
 * The selected rail is read from {@code CrossCollisionBlock.makeShapes} and
 * {@code CrossCollisionBlock.getCollisionShape}. In 26.2 unobfuscated,
 * {@code SoulSandBlock.getBlockSupportShape},
 * {@code FenceBlock.getStateForPlacement}, and {@code FenceBlock.updateShape}
 * make this built-in fence arm native again. This change leaves the stored
 * vanilla state untouched and returns only the audited central post.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class OakFenceSoulSandCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape POST = Shapes.box(6.0 / 16.0, 0.0, 6.0 / 16.0, 10.0 / 16.0, 24.0 / 16.0, 10.0 / 16.0);
    private static final Map<Direction, VoxelShape> ONE_ARM_SHAPES = Shapes.rotateHorizontal(Shapes.or(
        POST, Shapes.box(6.0 / 16.0, 0.0, 0.0, 10.0 / 16.0, 24.0 / 16.0, 0.5)));

    @Override
    public String blockId() {
        return "minecraft:oak_fence";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
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
        if (state.getBlock() != Blocks.OAK_FENCE || state.getValue(FenceBlock.WATERLOGGED)
            || restoredConnectionMask != 0 || !(level instanceof CollisionGetter collisionGetter)) {
            return Optional.empty();
        }

        Direction connected = null;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (state.getValue(FenceBlock.PROPERTY_BY_DIRECTION.get(direction))) {
                if (connected != null) {
                    return Optional.empty();
                }
                connected = direction;
            }
        }
        if (connected == null) {
            return Optional.empty();
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighborPos = pos.relative(direction).immutable();
            BlockGetter loadedChunk = collisionGetter.getChunkForCollisions(
                neighborPos.getX() >> 4,
                neighborPos.getZ() >> 4
            );
            if (loadedChunk == null) {
                return Optional.empty();
            }
            BlockState neighbor = loadedChunk.getBlockState(neighborPos);
            if (direction == connected) {
                if (!neighbor.is(Blocks.SOUL_SAND)) {
                    return Optional.empty();
                }
            } else if (!neighbor.is(Blocks.AIR)) {
                return Optional.empty();
            }
        }

        VoxelShape expectedNative = ONE_ARM_SHAPES.get(connected);
        if (Shapes.joinIsNotEmpty(vanillaShape, expectedNative, BooleanOp.NOT_SAME)) {
            return Optional.empty();
        }

        return Optional.of(POST);
    }

}
