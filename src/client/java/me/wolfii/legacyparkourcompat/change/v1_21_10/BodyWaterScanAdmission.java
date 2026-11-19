package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.BodyWaterScanAdmissionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

/**
 * Exact 1.14–1.21.10 client fluid admission uses an area guard whose virtual
 * client chunk check accepts empty chunks. Its ordinary body scan uses a
 * 0.001 inset, contextual height and inclusive surface overlap. The 1.16–1.16.5
 * explicit scan-admission change takes precedence before this fallback.
 *
 * <p>In 26.2 EntityFluidInteraction.hasFluidAndLoaded can reject when only a
 * padded neighbor is missing. Recover the historical body result for an
 * actually loaded ordinary water column without reopening the native scan.
 * Source and audit: development/research/source-evidence/20261118/
 * block-a-114-115-body-water-admission/ and development/research/source-evidence/
 * 20261119: block-a-118-119-body-water-admission, block-a-120-body-water-admission,
 * and block-a-121-body-water-admission.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class BodyWaterScanAdmission implements BodyWaterScanAdmissionHook {
    @Override
    public boolean admitsColumn(int minY, int maxY) {
        return minY >= 0 && maxY < 256;
    }

    @Override
    public Optional<Float> fallbackHeight(Level level, BlockPos pos, FluidState state) {
        BlockPos above = pos.above();
        if (level.isOutsideBuildHeight(pos) || level.isOutsideBuildHeight(above)
            || above.getY() >= 256 || !level.getBlockState(pos).is(Blocks.WATER)
            || (state.getType() != Fluids.WATER && state.getType() != Fluids.FLOWING_WATER)) {
            return Optional.empty();
        }
        // Both height dependencies occupy the already checked horizontal column.
        var aboveState = level.getBlockState(above);
        if (!aboveState.getFluidState().isEmpty() && !aboveState.is(Blocks.WATER)) {
            return Optional.empty();
        }
        // Exact 1.14–1.21.10 and current FlowingFluid use the same contextual
        // height formula: same fluid above means 1.0F, otherwise amount / 9.0F.
        return Optional.of(state.getType().isSame(aboveState.getFluidState().getType())
            ? 1.0F : state.getOwnHeight());
    }
}
