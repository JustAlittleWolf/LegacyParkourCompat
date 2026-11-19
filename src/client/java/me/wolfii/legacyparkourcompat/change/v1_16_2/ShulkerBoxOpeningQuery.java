package me.wolfii.legacyparkourcompat.change.v1_16_2;

import java.util.Set;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.ShulkerBoxOpeningQueryHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

/**
 * 1.11 Feather ShulkerBoxBlockEntity.tickAnimation/moveEntities/getLidShape and
 * 1.16.5 Mojmap ShulkerBoxBlockEntity.updateAnimation/moveCollidedEntities/getTopBoundingBox
 * query the whole protruding lid at interpolated half progress, including the last
 * increment before the OPENED transition. 1.17 Mojmap ShulkerBoxBlockEntity.updateAnimation
 * clamps before moveCollidedEntities uses Shulker.getProgressDeltaAabb at full progress.
 * 26.2 unobfuscated ShulkerBoxBlockEntity.updateAnimation also updates neighbors on
 * the first and final opening ticks before calling moveCollidedEntities.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class ShulkerBoxOpeningQuery implements ShulkerBoxOpeningQueryHook {
    private static final Set<Block> DYED_BOXES = Set.copyOf(Blocks.DYED_SHULKER_BOX.asList());

    @Override
    public boolean supports(Block block, ParkourVersion target) {
        if (target.olderThan(ParkourVersion.V1_11)) {
            return false;
        }
        // 1.11 Feather Block registers sixteen dyes. 1.13 Feather DataFixes' "Colorless
        // shulker block fixer" renames old purple to the plain box on converted maps.
        if (block == Blocks.SHULKER_BOX) {
            return true;
        }
        return DYED_BOXES.contains(block)
            && (block != Blocks.DYED_SHULKER_BOX.purple() || target.newerThanOrEqual(ParkourVersion.V1_13));
    }

    @Override
    public AABB bounds(BlockPos pos, Direction facing, float previousProgress, float progress) {
        // 1.16.5 Mojmap ShulkerBoxBlockEntity.getProgress(1.0F) calls Mth.lerp.
        float height = 0.5F * (previousProgress + 1.0F * (progress - previousProgress));
        Direction opposite = facing.getOpposite();
        return new AABB(0, 0, 0, 1, 1, 1)
            .expandTowards(height * facing.getStepX(), height * facing.getStepY(), height * facing.getStepZ())
            .contract(opposite.getStepX(), opposite.getStepY(), opposite.getStepZ())
            .move(pos);
    }
}
