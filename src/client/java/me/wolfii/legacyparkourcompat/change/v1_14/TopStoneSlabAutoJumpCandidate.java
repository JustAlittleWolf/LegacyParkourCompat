package me.wolfii.legacyparkourcompat.change.v1_14;

import java.util.List;
import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.collision.RawArrayVoxelShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook.ProbeSegments;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpPartialCandidateHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * 1.14 Ornithe Feather Gen2 build.2 WorldView.getCollisions (127–194) uses the
 * exact shape query and yields intersecting partials before the full-cube aggregate.
 * SlabBlock.getShape (46–53) returns the dry top-half Stone slab's single box.
 * LocalClientPlayerEntity.autoJump (898–951) stops at the first side-probe hit.
 * 1.14.1 Yarn build.10 ViewableWorld.getCollisionShapes (129–193) and
 * ClientPlayerEntity.method_3148 (886–940) instead consume per-cell boxes, so
 * the earlier stone cell reaches the one-block headroom sample first.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class TopStoneSlabAutoJumpCandidate implements AutoJumpPartialCandidateHook {
    private static final AABB TOP_HALF = new AABB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);

    @Override
    public Optional<Partial> candidate(Player player, Level level, AABB query, ProbeSegments probes,
                                      BlockState state, BlockPos slab, CollisionContext context) {
        if (!state.is(Blocks.STONE_SLAB) || state.getValue(SlabBlock.TYPE) != SlabType.TOP
            || state.getValue(SlabBlock.WATERLOGGED) || !state.getFluidState().isEmpty()
            || !(player instanceof LocalPlayer localPlayer) || Minecraft.getInstance().player != localPlayer
            || player.level() != level || !level.isClientSide() || player.getPose() != Pose.STANDING
            || level.getWorldBorder().getStatus() != BorderStatus.STATIONARY
            || !player.onGround() || player.noPhysics || player.isSpectator() || player.isPassenger()
            || player.isInWater() || player.isInLava() || player.getAbilities().flying
            || player.hasEffect(MobEffects.JUMP_BOOST)
            || player.getBbWidth() != 0.6F || player.getBbHeight() != 1.8F
            || player.getY() != slab.getY() || query.minY != player.getY()
            || query.maxY != player.getY() + player.getBbHeight()) {
            return Optional.empty();
        }
        AABB box = player.getBoundingBox();
        var feet = BlockPos.containing(player.getX(), player.getY(), player.getZ());
        if (!feet.equals(slab.west()) || box.maxX >= slab.getX() || box.minZ >= slab.getZ()
            || box.minY != query.minY) {
            return Optional.empty();
        }

        BlockPos stone = slab.north();
        if (!loadedIs(level, stone, Blocks.STONE) || !loadedIs(level, stone.above(), Blocks.STONE)
            || !loadedIs(level, feet.below(), Blocks.STONE) || !loadedIs(level, feet.north().below(), Blocks.STONE)
            || !loadedIs(level, slab.above(), Blocks.AIR) || !loadedIs(level, feet.above(), Blocks.AIR)
            || !loadedIs(level, feet.above(2), Blocks.AIR)) {
            return Optional.empty();
        }
        AABB slabBox = TOP_HALF.move(slab);
        AABB stoneBox = new AABB(stone);
        if (!crossesEitherProbe(probes, slabBox) || !crossesEitherProbe(probes, stoneBox)) {
            return Optional.empty();
        }

        var localShape = context.getCollisionShape(state, level, slab);
        if (!localShape.toAabbs().equals(List.of(TOP_HALF))) return Optional.empty();

        // The 1.14 query has a world Y extent outside the unit box, so
        // VoxelShapes.block(Box) retains raw coordinates for every axis.
        var translated = localShape.move(slab);
        return Shapes.joinIsNotEmpty(RawArrayVoxelShape.oneCell(query), translated, BooleanOp.AND)
            ? Optional.of(new Partial(translated, 4)) : Optional.empty();
    }

    private static boolean loadedIs(Level level, BlockPos pos, Block block) {
        var chunk = level.getChunkForCollisions(
            SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
        return chunk != null && !level.isOutsideBuildHeight(pos) && chunk.getBlockState(pos).is(block);
    }

    private static boolean crossesEitherProbe(ProbeSegments probes, AABB box) {
        return crossesPositiveX(probes.leftStart(), probes.leftEnd(), box)
            || crossesPositiveX(probes.rightStart(), probes.rightEnd(), box);
    }

    private static boolean crossesPositiveX(Vec3 begin, Vec3 end, AABB box) {
        return begin.x < box.minX && end.x > box.minX && end.x < box.maxX
            && begin.y == end.y && begin.y > box.minY && begin.y < box.maxY
            && begin.z == end.z && begin.z > box.minZ && begin.z < box.maxZ;
    }
}
