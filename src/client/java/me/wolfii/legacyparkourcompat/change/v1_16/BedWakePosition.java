package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.bed.BedWakePositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.Optional;

/**
 * Implements the paired 1.16.x bed wake search. The 1.16.1 Mojmap
 * BedBlock.findStandUpPosition/getStandingLocationAtOrBelow grid and support
 * algorithm is retained as a pair; 1.16.2 Mojmap BedBlock.findStandUpPosition
 * and DismountHelper.findSafeDismountLocation are the changed yaw-ordered pair.
 * Current 26.2 unobfuscated BedBlock.findStandUpPosition is the call-through
 * seam and remains the fallback outside the bounded air/stone/source-equivalent
 * bed query domain. The integration admission checks stay in MovementRuntime.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class BedWakePosition implements BedWakePositionHook {
    private static final double SUPPORT_EPSILON = 2.0E-7;

    public BedWakePosition() {
    }

    @Override
    public BedWakePositionHook.Result findStandUpPosition(
        Entity entity,
        EntityType<?> entityType,
        CollisionGetter level,
        BlockPos bedPosition
    ) {
        return findStandUpPositionInContext(entity, entityType, level, bedPosition);
    }

    private BedWakePositionHook.Result findStandUpPositionInContext(
        Entity entity, EntityType<?> entityType, CollisionGetter level, BlockPos bedPosition
    ) {
        Direction facing = level.getBlockState(bedPosition).getValue(BedBlock.FACING);
        int bedX = bedPosition.getX();
        int bedY = bedPosition.getY();
        int bedZ = bedPosition.getZ();

        // 1.16.1 Mojmap BedBlock.findStandUpPosition visits m, then x, then z.
        for (int m = 0; m <= 1; m++) {
            int firstX = bedX - facing.getStepX() * m - 1;
            int firstZ = bedZ - facing.getStepZ() * m - 1;
            for (int x = firstX; x <= firstX + 2; x++) {
                for (int z = firstZ; z <= firstZ + 2; z++) {
                    BedWakePositionHook.Result result = getStandingLocationAtOrBelow(
                        entity, entityType, level, new BlockPos(x, bedY, z)
                    );
                    if (!result.supported()) {
                        return result;
                    }
                    if (result.position().isPresent()) {
                        return result;
                    }
                }
            }
        }
        return BedWakePositionHook.Result.of(Optional.empty());
    }

    private BedWakePositionHook.Result getStandingLocationAtOrBelow(
        Entity entity,
        EntityType<?> entityType,
        CollisionGetter level,
        BlockPos candidate
    ) {
        VoxelShape initialShape = historicalCollisionShape(entity, level, candidate);
        if (initialShape.max(Direction.Axis.Y) > 0.4375) {
            return BedWakePositionHook.Result.of(Optional.empty());
        }

        BlockPos.MutableBlockPos support = candidate.mutable();
        while (
            support.getY() >= 0
                && candidate.getY() - support.getY() <= 2
                && historicalCollisionShape(entity, level, support).isEmpty()
        ) {
            support.move(Direction.DOWN);
        }

        VoxelShape supportShape = historicalCollisionShape(entity, level, support);
        if (supportShape.isEmpty()) {
            return BedWakePositionHook.Result.of(Optional.empty());
        }

        double y = support.getY() + supportShape.max(Direction.Axis.Y) + SUPPORT_EPSILON;
        if (candidate.getY() - y > 2.0) {
            return BedWakePositionHook.Result.of(Optional.empty());
        }

        Vec3 position = new Vec3(support.getX() + 0.5, y, support.getZ() + 0.5);
        AABB playerBox = entityType.getDimensions().makeBoundingBox(position);
        if (!level.noCollision(playerBox)) {
            return BedWakePositionHook.Result.of(Optional.empty());
        }

        // 1.16.1 Mojmap EntityType.isBlockDangerous is reproduced for the local
        // player. If the current type predicate differs (for example, a block
        // introduced after 1.16), keep the complete current vanilla search.
        boolean historicalDanger = false;
        boolean dangerBehaviorDiffers = false;
        for (BlockState state : level.getBlockStates(playerBox.expandTowards(0.0, -0.2F, 0.0)).toList()) {
            boolean oldPredicate = isHistoricalPlayerDangerous(state);
            historicalDanger |= oldPredicate;
            dangerBehaviorDiffers |= entityType.isBlockDangerous(state) != oldPredicate;
        }
        if (dangerBehaviorDiffers) {
            return BedWakePositionHook.Result.unsupported();
        }
        if (historicalDanger) {
            return BedWakePositionHook.Result.of(Optional.empty());
        }

        return BedWakePositionHook.Result.of(Optional.of(position));
    }

    private static VoxelShape historicalCollisionShape(Entity entity, CollisionGetter level, BlockPos pos) {
        return level.getBlockState(pos).getCollisionShape(level, pos, CollisionContext.of(entity));
    }

    private static boolean isHistoricalPlayerDangerous(BlockState state) {
        return state.is(BlockTags.FIRE)
            || state.is(Blocks.MAGMA_BLOCK)
            || CampfireBlock.isLitCampfire(state)
            || state.is(Blocks.LAVA)
            || state.is(Blocks.WITHER_ROSE)
            || state.is(Blocks.SWEET_BERRY_BUSH)
            || state.is(Blocks.CACTUS);
    }

}
