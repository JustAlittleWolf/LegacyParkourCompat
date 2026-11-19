package me.wolfii.legacyparkourcompat.change.v1_14;

import java.util.List;
import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook.ProbeSegments;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpEntityPrefixHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 1.14 Feather Gen2 build.2 WorldView.getCollisions (127–194) prepends the
 * entity stream supplied through WorldAccess (74–75) and EntityView (45–59).
 * BoatEntity.getCollisionShape (113–117) returns the registered 1.375 by
 * 0.5625 AABB. FenceBlock (24–30) and PaneBlock (31–45, 85–88) give the
 * dry unconnected fence its single post. LocalClientPlayerEntity.autoJump
 * (898–952) consumes the first side-ray hit.
 * 1.14.1 Yarn build.10 ViewableWorld.getCollisionShapes (129–194) instead
 * appends the entity stream for ClientPlayerEntity's AutoJump (900–903).
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class BoatAutoJumpEntityPrefix implements AutoJumpEntityPrefixHook {
    private static final AABB FENCE_POST = new AABB(0.375, 0.0, 0.375, 0.625, 1.5, 0.625);
    private static final double CONTACT_TOLERANCE = 1.0E-7;
    private static final double HISTORICAL_BORDER_LIMIT = 29_999_984;

    @Override
    public boolean observesClippedGroundedTravel() {
        return true;
    }

    @Override
    public Optional<List<VoxelShape>> candidates(Player player, Level level, AABB query, ProbeSegments probes,
                                                 Entity entity, BlockState state, BlockPos fence,
                                                 CollisionContext context) {
        if (!(player instanceof LocalPlayer local) || Minecraft.getInstance().player != local
            || player.level() != level || !level.isClientSide() || !OrdinaryPlayerContext.matches(player)
            || player.getPose() != Pose.STANDING || !player.onGround() || !player.horizontalCollision
            || player.noPhysics || player.isSpectator() || player.isSprinting() || player.isShiftKeyDown()
            || player.isInWater() || player.isInLava() || player.getAbilities().flying
            || player.hasEffect(MobEffects.JUMP_BOOST) || player.getSpeed() != 0.1F
            || player.getYRot() != 0.0F || player.getBbWidth() != 0.6F || player.getBbHeight() != 1.8F
            || player.getY() != fence.getY() || query.minY != player.getY()
            || query.maxY != player.getY() + player.getBbHeight()) {
            return Optional.empty();
        }
        var start = probes.groundedTravelStart();
        if (start == null) return Optional.empty();
        var keys = local.input.keyPresses;
        if (!keys.forward() || keys.backward() || keys.left() || keys.right() || keys.jump()
            || entity.getType() != EntityTypes.OAK_BOAT || !entity.isAlive() || entity.noPhysics
            || entity.isPassenger() || !entity.getPassengers().isEmpty() || entity.isInWater() || entity.isInLava()
            || entity.getY() != player.getY() || !state.is(Blocks.OAK_FENCE)
            || state.getValue(FenceBlock.WATERLOGGED) || state.getValue(FenceBlock.NORTH)
            || state.getValue(FenceBlock.SOUTH) || state.getValue(FenceBlock.WEST) || state.getValue(FenceBlock.EAST)) {
            return Optional.empty();
        }
        var border = level.getWorldBorder();
        if (border.getStatus() != BorderStatus.STATIONARY
            || query.minX <= Math.max(border.getMinX(), -HISTORICAL_BORDER_LIMIT) + 2.0
            || query.maxX >= Math.min(border.getMaxX(), HISTORICAL_BORDER_LIMIT) - 2.0
            || query.minZ <= Math.max(border.getMinZ(), -HISTORICAL_BORDER_LIMIT) + 2.0
            || query.maxZ >= Math.min(border.getMaxZ(), HISTORICAL_BORDER_LIMIT) - 2.0) {
            return Optional.empty();
        }

        AABB boat = entity.getBoundingBox();
        AABB expectedBoat = new AABB(entity.getX() - 0.6875, entity.getY(), entity.getZ() - 0.6875,
            entity.getX() + 0.6875, entity.getY() + 0.5625, entity.getZ() + 0.6875);
        VoxelShape localFence = context.getCollisionShape(state, level, fence);
        AABB post = FENCE_POST.move(fence);
        AABB standing = player.getBoundingBox();
        if (!boat.equals(expectedBoat) || !localFence.toAabbs().equals(List.of(FENCE_POST))
            || !query.intersects(boat) || !query.intersects(post)
            || Math.abs(standing.maxZ - post.minZ) > CONTACT_TOLERANCE || boat.minZ <= standing.maxZ
            || start.z + (standing.maxZ - player.getZ()) >= post.minZ
            || boat.maxX >= post.minX
            || boat.minX < Math.floor(query.minX) - 1.0 || boat.maxX > Math.ceil(query.maxX) + 1.0
            || boat.minZ < Math.floor(query.minZ) - 1.0 || boat.maxZ > Math.ceil(query.maxZ) + 1.0
            || !crossesPositiveZ(probes.leftStart(), probes.leftEnd(), post)
            || !crossesPositiveZ(probes.rightStart(), probes.rightEnd(), boat)) {
            return Optional.empty();
        }
        var cap = BlockPos.containing(player.getX(), standing.maxY, player.getZ());
        if (!loadedAir(level, cap) || !loadedAir(level, cap.above())
            || !loadedAir(level, BlockPos.containing(boat.getCenter()).above())
            || !loadedAir(level, BlockPos.containing(post.getCenter()).above())) {
            return Optional.empty();
        }

        // Require the actual native collision entity shape to match the old Boat AABB.
        var entities = level.getEntityCollisions(player, query);
        if (entities.size() != 1 || !entities.getFirst().toAabbs().equals(List.of(boat))) {
            return Optional.empty();
        }
        return Optional.of(List.of(entities.getFirst(), localFence.move(fence)));
    }

    private static boolean crossesPositiveZ(Vec3 begin, Vec3 end, AABB box) {
        return begin.z < box.minZ && end.z > box.minZ
            && begin.x == end.x && begin.x > box.minX && begin.x < box.maxX
            && begin.y == end.y && begin.y > box.minY && begin.y < box.maxY;
    }

    private static boolean loadedAir(Level level, BlockPos pos) {
        var chunk = level.getChunkForCollisions(
            SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
        return chunk != null && !level.isOutsideBuildHeight(pos) && chunk.getBlockState(pos).is(Blocks.AIR);
    }
}
