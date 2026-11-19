package me.wolfii.legacyparkourcompat.change.v1_10_1;

import java.util.List;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.CollisionAxisOrderContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.collision.MovementCollisionContext;
import me.wolfii.legacyparkourcompat.mechanic.collision.PlayerPoseCollisionContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.LowerRowCollisionCandidateHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.PistonMovingBlockEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;

/**
 * 1.10 through 1.10.2 Feather Gen2 build.2 World.getCollisions (1.10.2:
 * 903–923) omits the lower Y row on one horizontal perimeter. Even when
 * queried, MovingBlockEntity.getShape (80–88) uses the fence outline
 * (FenceBlock.java:27–43,78–82), which ends below this player.
 * 26.2 BlockCollisions.computeNext (79–101) reaches the dynamic moving
 * piston and its translated 1.5-high collision post in this same sweep.
 *
 * In all three Ornithe Feather versions, PlayerEntity.tick calls
 * updatePlayerPose (296–320) after travel. Releasing sneak keeps the 1.65F
 * box during travel, then checks the 1.8F box with World.getCollisions(Box)
 * (1022–1059). That overload also skips the lower-row post. Even if it
 * visited the owner, the moved outline ends below both boxes. In 26.2,
 * unobfuscated Player.updatePlayerPose (343–374) checks SWIMMING and then
 * STANDING. Both fits must omit this post for the player to stand after travel.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class LowerRowMovingFenceCollision implements LowerRowCollisionCandidateHook {
    private static final AABB WEST_POST = new AABB(0.875, 0.0, 0.375, 1.125, 1.5, 0.625);
    private static final double QUERY_EPSILON = 1.0E-7;

    @Override
    public boolean excludeCandidate(BlockState state, CollisionGetter level, BlockPos pos, AABB query,
                                    CollisionContext context) {
        if (!state.is(Blocks.MOVING_PISTON) || !(MovementRuntime.playerFrom(context) instanceof LocalPlayer player)
            || Minecraft.getInstance().player != player || player.level() != level
            || !OrdinaryPlayerContext.matches(player)
            || player.noPhysics || player.isSpectator() || player.onGround() || player.isShiftKeyDown()
            || player.isInWater() || player.isInLava() || player.onClimbable() || player.getAbilities().flying
            || player.getBbWidth() != 0.6F) {
            return false;
        }
        boolean releasingCrouch = player.getPose() == Pose.CROUCHING && player.getBbHeight() == 1.65F;
        boolean standing = player.getPose() == Pose.STANDING && player.getBbHeight() == 1.8F;
        AABB post = WEST_POST.move(pos);
        boolean poseFit = releasingCrouch && PlayerPoseCollisionContext.isCheckingPose(player);
        boolean sweep = (standing || releasingCrouch) && isWestwardSweep(player, context, query, post);
        if (!poseFit && !sweep) {
            return false;
        }

        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        // The relative interval locates the owner on exactly one horizontal
        // perimeter and the lower padded Y row. The old outline is below
        // the query in both the sweep and the stand clearance check.
        if (!(query.minX > x + 1.0 + QUERY_EPSILON && query.minX < post.maxX - QUERY_EPSILON
            && query.maxX < x + 2.0 - QUERY_EPSILON
            && query.minY > y + 1.0 + QUERY_EPSILON && query.minY < post.maxY - QUERY_EPSILON
            && query.minZ > z + QUERY_EPSILON && query.maxZ < z + 1.0 - QUERY_EPSILON
            && query.minZ < post.maxZ - QUERY_EPSILON && query.maxZ > post.minZ + QUERY_EPSILON
            && y >= 0 && query.maxY < 256.0)) {
            return false;
        }

        var world = player.level();
        var border = world.getWorldBorder();
        if (border.getStatus() != BorderStatus.STATIONARY
            || !(query.minX > border.getMinX() + 2.0 && query.maxX < border.getMaxX() - 2.0
                && query.minZ > border.getMinZ() + 2.0 && query.maxZ < border.getMaxZ() - 2.0)
            || !world.getEntities(player, query.inflate(0.25), entity -> true).isEmpty()) {
            return false;
        }

        var chunk = world.getChunkForCollisions(x >> 4, z >> 4);
        if (chunk == null || chunk.getBlockState(pos) != state
            || !(chunk.getBlockEntity(pos) instanceof PistonMovingBlockEntity piston)
            || piston.isSourcePiston() || !piston.isExtending() || piston.getDirection() != Direction.WEST) {
            return false;
        }
        var access = (PistonMovingBlockEntityAccessor) piston;
        BlockState moved = piston.getMovedState();
        return access.legacyparkourcompat$progress() == 0.5F
            && Float.floatToRawIntBits(access.legacyparkourcompat$previousProgress()) == 0
            && moved.is(Blocks.OAK_FENCE) && moved.getFluidState().isEmpty() && !moved.getValue(FenceBlock.WATERLOGGED)
            && !moved.getValue(FenceBlock.NORTH) && !moved.getValue(FenceBlock.SOUTH)
            && !moved.getValue(FenceBlock.EAST) && !moved.getValue(FenceBlock.WEST)
            && piston.getCollisionShape(level, pos).toAabbs().equals(List.of(WEST_POST));
    }

    private static boolean isWestwardSweep(LocalPlayer player, CollisionContext context, AABB query, AABB post) {
        if (!(context instanceof MovementCollisionContext movementContext) || !movementContext.query().equals(query)) {
            return false;
        }
        var movement = CollisionAxisOrderContext.movementQuery();
        if (movement == null || movement.entity() != player || movement.moverType() != MoverType.SELF
            || !movement.unadjustedPrimaryMovement() || !movement.sweptBox().equals(query)
            || !movement.startingBox().equals(player.getBoundingBox())
            || movement.startingBox().minX <= post.maxX + QUERY_EPSILON) {
            return false;
        }
        var request = movement.requestedMovement();
        return request.x < 0.0 && request.y == 0.0 && request.z == 0.0;
    }
}
