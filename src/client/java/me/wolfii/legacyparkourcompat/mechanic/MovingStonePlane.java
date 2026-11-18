package me.wolfii.legacyparkourcompat.mechanic;

import java.util.List;
import java.util.OptionalDouble;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AxisCollisionConstraintHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.PistonMovingBlockEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

/** Validates the solitary moving-Stone source before substituting its old axis constraint. */
final class MovingStonePlane {
    private MovingStonePlane() {}

    static OptionalDouble clip(@Nullable Entity entity, Direction.Axis axis, AABB moving,
                               Iterable<VoxelShape> shapes, double distance, Vec3 request, AABB start) {
        var hook = MovementRuntime.find(AxisCollisionConstraintHook.class, entity).orElse(null);
        var query = CollisionAxisOrderContext.movementQuery();
        if (hook == null || !(entity instanceof LocalPlayer player) || Minecraft.getInstance().player != player
            || query == null || query.entity() != player || query.moverType() != MoverType.SELF
            || !query.unadjustedPrimaryMovement() || !query.requestedMovement().equals(request)
            || !query.startingBox().equals(start) || !start.equals(player.getBoundingBox())
            || !(shapes instanceof List<?> list) || list.size() != 1 || !(list.getFirst() instanceof VoxelShape shape)) {
            return OptionalDouble.empty();
        }

        var source = CollisionShapeSourceContext.source(shape);
        if (source == null || !source.state().is(Blocks.MOVING_PISTON) || !eligible(player, request)
            || PistonMovingBlockEntityAccessor.legacyparkourcompat$noClip().get() != null) {
            return OptionalDouble.empty();
        }

        AABB afterY = start.move(0.0, request.y, 0.0);
        if (axis == Direction.Axis.Y) {
            if (distance != request.y || !moving.equals(start.move(Vec3.ZERO))) return OptionalDouble.empty();
        } else if (axis != Direction.Axis.X || distance != request.x || !moving.equals(afterY)) {
            return OptionalDouble.empty();
        }

        BlockPos pos = source.pos();
        var level = player.level();
        var chunk = level.getChunkForCollisions(
            SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
        if (chunk == null || chunk.getBlockState(pos) != source.state()
            || !(chunk.getBlockEntity(pos) instanceof PistonMovingBlockEntity piston)
            || piston.getDirection() != Direction.EAST || !piston.isExtending() || piston.isSourcePiston()
            || !piston.getMovedState().is(Blocks.STONE)) {
            return OptionalDouble.empty();
        }
        var progress = (PistonMovingBlockEntityAccessor) piston;
        if (progress.legacyparkourcompat$progress() != 0.5F
            || Float.floatToRawIntBits(progress.legacyparkourcompat$previousProgress()) != 0) {
            return OptionalDouble.empty();
        }

        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        List<AABB> parts = shape.toAabbs();
        AABB nativeBox = new AABB(x - 0.5, y, z, x + 0.5, y + 1.0, z + 1.0);
        if (parts.size() != 1 || !nativeBox.equals(parts.getFirst())) return OptionalDouble.empty();

        AABB plane = new AABB(x, y, z, x, y + 1.0, z + 1.0);
        AABB swept = query.sweptBox();
        if (start.maxX != x || !swept.intersects(plane)
            || start.minY >= y + 1.0 || afterY.minY <= y
            || start.minZ <= z || start.maxZ >= z + 1.0
            || !oldEnvelopeContainsOnlyCandidate(player, swept, pos, source.state())) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(hook.clip(axis, moving, plane, distance));
    }

    private static boolean eligible(LocalPlayer player, Vec3 request) {
        return player.getPose() == Pose.STANDING && OrdinaryPlayerContext.matches(player)
            && !player.noPhysics && !player.isSpectator() && !player.onGround() && !player.isShiftKeyDown()
            && !player.isInWater() && !player.isInLava() && !player.onClimbable() && !player.getAbilities().flying
            && player.getBbWidth() == 0.6F && player.getBbHeight() == 1.8F
            && Double.isFinite(request.x) && Double.isFinite(request.y)
            && request.x > 0.0 && request.y < 0.0 && request.z == 0.0;
    }

    private static boolean oldEnvelopeContainsOnlyCandidate(LocalPlayer player, AABB swept,
                                                             BlockPos piston, BlockState state) {
        var level = player.level();
        var border = level.getWorldBorder();
        if (border.getStatus() != BorderStatus.STATIONARY
            || !(swept.minX > border.getMinX() + 2.0 && swept.maxX < border.getMaxX() - 2.0
            && swept.minZ > border.getMinZ() + 2.0 && swept.maxZ < border.getMaxZ() - 2.0
            && swept.minY >= 0.0 && swept.maxY < 256.0)) return false;

        // 1.8.9 World.getCollisions visits one lower row and uses floor(max + 1)
        // as its exclusive upper endpoint. Other blocks must be ordinary air.
        int minX = Mth.floor(swept.minX), maxX = Mth.floor(swept.maxX + 1.0);
        int minY = Mth.floor(swept.minY) - 1, maxY = Mth.floor(swept.maxY + 1.0);
        int minZ = Mth.floor(swept.minZ), maxZ = Mth.floor(swept.maxZ + 1.0);
        long sizeX = (long) maxX - minX;
        if (sizeX <= 0 || sizeX > 4_096 || sizeX * (maxY - minY) * (maxZ - minZ) > 4_096
            || piston.getX() < minX || piston.getX() >= maxX
            || piston.getY() < minY || piston.getY() >= maxY
            || piston.getZ() < minZ || piston.getZ() >= maxZ
            || !level.getEntities(player, swept.inflate(0.25), entity -> true).isEmpty()) {
            return false;
        }

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                var chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
                if (chunk == null) return false;
                for (int y = minY; y < maxY; y++) {
                    cursor.set(x, y, z);
                    var neighbor = chunk.getBlockState(cursor);
                    boolean air = y < 0 ? neighbor.isAir() : neighbor.is(Blocks.AIR);
                    if (cursor.equals(piston) ? neighbor != state : !air) return false;
                }
            }
        }
        return true;
    }
}
