package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.CollisionAxisOrderContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.MovingPistonCollisionShapeHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * 1.8 through 1.8.9, Ornithe Feather Gen2 build 2:
 * MovingBlock.getCollisionShape trims the destination-owned Stone box using
 * MovingBlockEntity.getProgress(0.0F). In 1.9, MovingBlock.getCollisionShape
 * delegates to MovingBlockEntity.getShape, which unions translated boxes.
 * In 26.2 (unobfuscated), PistonMovingBlockEntity.tick copies progress to
 * progressO before advancing by 0.5F, and getCollisionShape translates the
 * full moved block shape using current progress.
 *
 * <p>Plain Glass uses that same full cube in 1.8.9 Block.java:863,
 * 1.9 Block.java:710, and 26.2 Blocks.java:648–660. At the accepted EAST
 * phase, 1.8.9 MovingBlock.java:118–130,162–193 gives maxX=0.5; 1.9
 * MovingBlockEntity.java:75–88 unions the displaced cube with the full
 * current cube. 26.2 PistonMovingBlockEntity.java:366–393 uses the full
 * current cube. The Glass lane checks the original positive-margin sweep.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class ExtendingStoneMovingPistonCollisionShape implements MovingPistonCollisionShapeHook {
    private static final AABB UNIT = new AABB(0, 0, 0, 1, 1, 1);
    private static final double QUERY_EPSILON = 1.0E-7;

    @Override
    public Optional<VoxelShape> collisionShape(BlockState movedState, Direction direction,
                                               boolean extending, boolean source,
                                               float progress, float previousProgress, BlockGetter level,
                                               BlockPos owner, CollisionContext context, VoxelShape nativeShape) {
        boolean glass = movedState.is(Blocks.GLASS);
        if (!extending || source || (!movedState.is(Blocks.STONE) && !glass)
            || progress != 1.0F || previousProgress != 0.5F) {
            // The zero-thickness old plane and terminal cube keep the native route.
            return Optional.empty();
        }
        var movement = CollisionAxisOrderContext.movementQuery();
        if (movement == null || movement.moverType() != MoverType.SELF || movement.entity().isPassenger()) {
            return Optional.empty();
        }

        if (glass && (direction != Direction.EAST || !admitsGlass(movement, level, owner, context, nativeShape))) {
            return Optional.empty();
        }

        float trim = 1.0F - previousProgress;
        double minX = 0.0;
        double minY = 0.0;
        double minZ = 0.0;
        double maxX = 1.0;
        double maxY = 1.0;
        double maxZ = 1.0;
        switch (direction) {
            case EAST -> maxX -= trim;
            case WEST -> minX += trim;
            case UP -> maxY -= trim;
            case DOWN -> minY += trim;
            case SOUTH -> maxZ -= trim;
            case NORTH -> minZ += trim;
        }
        return Optional.of(Shapes.box(minX, minY, minZ, maxX, maxY, maxZ));
    }

    private static boolean admitsGlass(CollisionAxisOrderContext.MovementQuery movement, BlockGetter level,
                                       BlockPos owner, CollisionContext context, VoxelShape nativeShape) {
        if (!(movement.entity() instanceof LocalPlayer player) || MovementRuntime.playerFrom(context) != player
            || player.level() != level || !movement.unadjustedPrimaryMovement() || !OrdinaryPlayerContext.matches(player)
            || player.getPose() != Pose.STANDING || player.noPhysics || player.isSpectator() || player.onGround()
            || player.isShiftKeyDown() || player.isInWater() || player.isInLava() || player.onClimbable()
            || player.getAbilities().flying || player.getBbWidth() != 0.6F || player.getBbHeight() != 1.8F
            || !nativeShape.toAabbs().equals(List.of(UNIT))) {
            return false;
        }

        var request = movement.requestedMovement();
        AABB start = movement.startingBox();
        AABB swept = movement.sweptBox();
        int x = owner.getX(), y = owner.getY(), z = owner.getZ();
        if (!(request.x < 0.0 && request.y == 0.0 && request.z == 0.0 && start.equals(player.getBoundingBox())
            && start.minX > x + 1.0 + QUERY_EPSILON && start.minX < x + 1.5 && start.maxX < x + 2.0
            && swept.minX > x + 0.5 + QUERY_EPSILON && swept.minX < x + 1.0 - QUERY_EPSILON
            && start.minY > y + QUERY_EPSILON && start.minY < y + 1.0 - QUERY_EPSILON
            && start.minZ > z + QUERY_EPSILON && start.maxZ < z + 1.0 - QUERY_EPSILON)) {
            return false;
        }

        var world = player.level();
        var border = world.getWorldBorder();
        if (swept.minY < 0.0 || swept.maxY >= 256.0 || border.getStatus() != BorderStatus.STATIONARY
            || !(swept.minX > border.getMinX() + 2.0 && swept.maxX < border.getMaxX() - 2.0
                && swept.minZ > border.getMinZ() + 2.0 && swept.maxZ < border.getMaxZ() - 2.0)
            || !world.getEntities(player, swept.inflate(0.25), entity -> true).isEmpty()) {
            return false;
        }

        // The old World query starts at floor(swept.minX), so it visits the
        // Glass owner but never the paired head one cell west. Check the
        // padded native envelope too for earlier profiles using its cursor.
        var cursor = new BlockPos.MutableBlockPos();
        BlockPos head = owner.west();
        int minY = Mth.floor(swept.minY - QUERY_EPSILON) - 1;
        int maxY = Mth.floor(swept.maxY + QUERY_EPSILON) + 1;
        for (int cx = x - 1; cx <= x + 2; cx++) {
            for (int cz = z - 1; cz <= z + 1; cz++) {
                var chunk = world.getChunkForCollisions(cx >> 4, cz >> 4);
                if (chunk == null) return false;
                for (int cy = minY; cy <= maxY; cy++) {
                    cursor.set(cx, cy, cz);
                    var state = chunk.getBlockState(cursor);
                    if (cursor.equals(owner) && state.is(Blocks.MOVING_PISTON)) continue;
                    if (cy < 0 ? state.isAir() : state.is(Blocks.AIR)) continue;
                    if (cursor.equals(head) && state.is(Blocks.MOVING_PISTON)
                        && chunk.getBlockEntity(cursor) instanceof PistonMovingBlockEntity piston
                        && piston.isExtending() && piston.isSourcePiston() && piston.getDirection() == Direction.EAST
                        && piston.getCollisionShape(world, cursor).max(Direction.Axis.X) <= 1.0) continue;
                    return false;
                }
            }
        }
        return true;
    }
}
