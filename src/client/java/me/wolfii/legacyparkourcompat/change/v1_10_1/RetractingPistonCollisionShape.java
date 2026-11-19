package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.CollisionAxisOrderContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.MovingPistonCollisionShapeHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * 1.10.2, ornithe-feather build.2: MovingBlock.getCollisionShape dispatches to
 * MovingBlockEntity.getShape(WorldView, BlockPos), which encloses the moved
 * state's outline at current and last progress. PistonBaseBlock.doEvent's
 * type-1 source stores an unextended normal/sticky base; getShape is a full cube.
 * This assembly is shared by the checked 1.9 through 1.10.2 releases.
 *
 * 1.11, ornithe-feather build.2: MovingBlock.addCollisions instead delegates to
 * MovingBlockEntity.addCollisions, adding a fixed extended base and the head's
 * separate collision boxes at current progress. The old getShape union still
 * exists but is no longer used by ordinary collision collection.
 *
 * 26.2, unobfuscated: MovingPistonBlock.getCollisionShape delegates to
 * PistonMovingBlockEntity.getCollisionShape, retaining that separate assembly.
 *
 * <p>For the non-source EAST Stone fixture, 1.9 Feather Gen2 build.2
 * MovingBlock.java:130–134 and MovingBlockEntity.java:75–88 admit both
 * progress samples via World.java:893–928. The first 1.11 stable
 * MovingBlockEntity.addCollisions (295–316) takes only the current sample.
 * The previous box is needed even though the 26.2 native shape misses the sweep.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class RetractingPistonCollisionShape implements MovingPistonCollisionShapeHook {
    private static final AABB STONE_CURRENT = new AABB(0.5, 0, 0, 1.5, 1, 1);
    private static final double QUERY_EPSILON = 1.0E-7;

    @Override
    public Optional<VoxelShape> collisionShape(BlockState movedState, Direction direction,
                                              boolean extending, boolean source,
                                              float progress, float previousProgress, BlockGetter level,
                                              BlockPos owner, CollisionContext context, VoxelShape nativeShape) {
        if (extending) return Optional.empty();
        if (source) {
            if (!(movedState.is(Blocks.PISTON) || movedState.is(Blocks.STICKY_PISTON))
                || movedState.getValue(PistonBaseBlock.EXTENDED)) {
                return Optional.empty();
            }
        } else if (!movedState.is(Blocks.STONE) || direction != Direction.EAST
            || progress != 0.5F || Float.floatToRawIntBits(previousProgress) != 0
            || !admitsStone(level, owner, context, nativeShape)) {
            return Optional.empty();
        }

        // getAmountExtended and each direction multiplication remain float
        // operations before widening to the double arguments of Box.moved.
        float currentOffset = 1.0F - progress;
        float previousOffset = 1.0F - previousProgress;
        AABB cube = new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        AABB current = cube.move(currentOffset * direction.getStepX(),
            currentOffset * direction.getStepY(), currentOffset * direction.getStepZ());
        AABB previous = cube.move(previousOffset * direction.getStepX(),
            previousOffset * direction.getStepY(), previousOffset * direction.getStepZ());
        return Optional.of(Shapes.create(current.minmax(previous)));
    }

    private static boolean admitsStone(BlockGetter level, BlockPos owner, CollisionContext context,
                                       VoxelShape nativeShape) {
        var movement = CollisionAxisOrderContext.movementQuery();
        if (movement == null || !(movement.entity() instanceof LocalPlayer player)
            || Minecraft.getInstance().player != player || MovementRuntime.playerFrom(context) != player
            || player.level() != level || movement.moverType() != MoverType.SELF || !movement.unadjustedPrimaryMovement()
            || !OrdinaryPlayerContext.matches(player) || player.getPose() != Pose.STANDING
            || player.noPhysics || player.isSpectator() || player.isShiftKeyDown()
            || player.isInWater() || player.isInLava() || player.onClimbable() || player.getAbilities().flying
            || player.getBbWidth() != 0.6F || player.getBbHeight() != 1.8F || player.maxUpStep() != 0.6F
            || !nativeShape.toAabbs().equals(List.of(STONE_CURRENT))) {
            return false;
        }

        var request = movement.requestedMovement();
        AABB start = movement.startingBox();
        AABB swept = movement.sweptBox();
        int x = owner.getX(), y = owner.getY(), z = owner.getZ();
        double previousFace = x + 2.0;
        if (!(request.x < 0.0 && request.y == 0.0 && request.z == 0.0 && start.equals(player.getBoundingBox())
            && start.minX > previousFace + QUERY_EPSILON && start.maxX < x + 3.0 - QUERY_EPSILON
            && swept.minX > x + 1.5 + QUERY_EPSILON && swept.minX < previousFace - QUERY_EPSILON
            && start.minY == y && start.maxY == y + player.getBbHeight()
            && start.minZ > z + QUERY_EPSILON && start.maxZ < z + 1.0 - QUERY_EPSILON
            && y >= 1 && swept.maxY < 256.0)) {
            return false;
        }

        var world = player.level();
        var border = world.getWorldBorder();
        if (border.getStatus() != BorderStatus.STATIONARY
            || !(swept.minX > border.getMinX() + 3.0 && swept.maxX < border.getMaxX() - 3.0
                && swept.minZ > border.getMinZ() + 2.0 && swept.maxZ < border.getMaxZ() - 2.0)
            || !world.getEntities(player, swept.inflate(0.25), entity -> true).isEmpty()) {
            return false;
        }

        // The owner is on one outer X face of both scans. The native
        // moving-piston state is dynamic and admits its shape on that face.
        // The floor touches the query at minY and cannot add a step candidate.
        var cursor = new BlockPos.MutableBlockPos();
        for (int cx = x; cx <= x + 3; cx++) {
            for (int cz = z - 1; cz <= z + 1; cz++) {
                var chunk = world.getChunkForCollisions(cx >> 4, cz >> 4);
                if (chunk == null) return false;
                for (int cy = y - 2; cy <= y + 2; cy++) {
                    cursor.set(cx, cy, cz);
                    BlockState state = chunk.getBlockState(cursor);
                    if (cursor.equals(owner) && state.is(Blocks.MOVING_PISTON)) continue;
                    boolean floor = cy == y - 1 && cz == z && (cx == x + 1 || cx == x + 2);
                    if (state.is(Blocks.STONE) && cy < y) continue;
                    if (!floor && (cy < 0 ? state.isAir() : state.is(Blocks.AIR))) continue;
                    return false;
                }
            }
        }
        return true;
    }
}
