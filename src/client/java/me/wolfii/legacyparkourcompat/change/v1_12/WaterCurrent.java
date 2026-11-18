package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.water.LegacyWaterFlowMath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;
import java.util.Set;

/**
 * 1.12/1.12.1/1.12.2 Feather: World.applyLiquidDrag, LiquidBlock.getFlow,
 * PlayerEntity.hasLiquidCollision, Vec3d.normalize/length and MathHelper.sqrt(double).
 * Raw 1.8.0/1.8.1 Feather Gen2 bytecode and the byte-identical 1.8.2-1.8.9
 * Feather sources: World.applyLiquidDrag, LiquidBlock.applyMaterialDrag,
 * LiquidBlock.getLiquidDepth/getFlow and PlayerEntity.hasLiquidCollision
 * preserve the same coupled flow scan and player-flight gate. The accepted
 * 1.8.9 source has getLiquidDepth/getFlow at LiquidBlock.java:53-56, 129-165,
 * and PlayerEntity.hasLiquidCollision at PlayerEntity.java:1617-1619. The
 * adjacent 1.9.4 Feather sources retain the same horizontal depth-difference
 * flow, per-cell/aggregate normalization and nonflying impulse gates. This hook
 * returns empty for dependencies outside its bounded provider set, leaving
 * those cases on the native path.
 * Replaced in 1.13 Feather: Entity.m_69693160 (player averaging/depth weighting);
 * 26.2 unobfuscated: EntityFluidInteraction.Tracker.applyCurrentTo.
 * Normalize the sum even for players; float-rounded sqrt is historical behavior.
 * Supported ordinary nonfalling water flow is supplied by the shared scan.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterCurrent implements WaterCurrentHook {
    private static final Set<Block> STAINED_GLASS = Set.copyOf(Blocks.STAINED_GLASS.asList());

    public boolean replacesNativeCurrent() { return true; }
    public Vec3 accumulate(Vec3 total, Vec3 flow) { return total.add(flow); }
    public Vec3 impulse(Vec3 total, boolean flying) {
        if (LegacyWaterFlowMath.length(total) > 0.0 && !flying) {
            Vec3 normalized = LegacyWaterFlowMath.normalize(total);
            return new Vec3(normalized.x * 0.014, normalized.y * 0.014, normalized.z * 0.014);
        }
        return Vec3.ZERO;
    }

    /**
     * LiquidBlock.getFlow/getLiquidDepth, ordinary nonfalling branch only.
     * Falling centers need a historical wall-face provider and stay native.
     * The blocking-material provider is bounded to ordinary air and these
     * full solids (1.12-family Block bootstrap and Material.blocksMovement).
     * World.getBlockState/isOutsideWorldHeight returns air outside [0, 256) in that
     * family; historicalState preserves those empty neighbor reads. Unsupported
     * block/material dependencies reject this bounded bundle and retain native flow.
     */
    public Optional<Vec3> flow(Level level, BlockPos pos, BlockState state) {
        if (!historicalHeight(pos)) return Optional.empty();
        double x = 0.0, y = 0.0, z = 0.0;
        int depth = state.getValue(LiquidBlock.LEVEL);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos adjacent = pos.relative(direction);
            BlockState neighbor = historicalState(level, adjacent);
            boolean dryLadderSide = admitsDryLadderSide(level, pos, state, direction, adjacent, neighbor);
            if (!supported(neighbor) && !isAdditionalDrySideBlocker(neighbor) && !dryLadderSide) return Optional.empty();
            int other = depth(neighbor);
            if (other < 0) {
                if (neighbor.isAir() || dryLadderSide) {
                    BlockPos belowPosition = adjacent.below();
                    BlockState below = historicalState(level, belowPosition);
                    if (!supported(below) || (dryLadderSide && below != Blocks.WATER.defaultBlockState())) {
                        return Optional.empty();
                    }
                    other = depth(below);
                    if (other >= 0) {
                        int difference = other - (depth - 8);
                        x += direction.getStepX() * difference;
                        y += direction.getStepY() * difference;
                        z += direction.getStepZ() * difference;
                    }
                }
            } else {
                int difference = other - depth;
                x += direction.getStepX() * difference;
                y += direction.getStepY() * difference;
                z += direction.getStepZ() * difference;
            }
        }
        return Optional.of(LegacyWaterFlowMath.normalize(new Vec3(x, y, z)));
    }

    /**
     * In 1.8 through 1.12.2 Ornithe Feather, the registered ladder has
     * Material.DECORATION, so LiquidBlock.getFlow can read below its dry side.
     * Source and audit: development/research/source-evidence/20261118/
     * block-a-nonfalling-water-ladder-side/.
     */
    private static boolean admitsDryLadderSide(Level level, BlockPos centerPos, BlockState center,
                                               Direction direction, BlockPos side, BlockState ladder) {
        if (!ladder.is(Blocks.LADDER) || direction != Direction.EAST
            || ladder.getValue(LadderBlock.FACING) != Direction.WEST || !ladder.getFluidState().isEmpty()
            || center != Blocks.WATER.defaultBlockState()) {
            return false;
        }

        BlockPos support = side.east();
        BlockPos below = side.below();
        return historicalHeight(below)
            && !level.isOutsideBuildHeight(side) && !level.isOutsideBuildHeight(below)
            && level.hasChunkAt(centerPos) && level.hasChunkAt(side) && level.hasChunkAt(support)
            && level.getBlockState(support).is(Blocks.STONE);
    }

    private static boolean historicalHeight(BlockPos pos) {
        return pos.getY() >= 0 && pos.getY() < 256;
    }

    private static BlockState historicalState(Level level, BlockPos pos) {
        // 1.8.9 World.getBlockState returns air outside its [0, 256) height.
        return historicalHeight(pos) ? level.getBlockState(pos) : Blocks.AIR.defaultBlockState();
    }

    private static boolean supported(BlockState state) {
        if (state.getFluidState().is(FluidTags.WATER)) {
            return state.getBlock() instanceof LiquidBlock && state.getValue(LiquidBlock.LEVEL) < 8;
        }
        return state.isAir() || state.is(Blocks.STONE) || state.is(Blocks.COBBLESTONE)
            || state.is(Blocks.DIRT) || state.is(Blocks.BEDROCK);
    }

    /**
     * 1.8 through 1.12.2 Ornithe Feather: GLASS and LEAVES materials block
     * movement in LiquidBlock.getFlow, so this side skips the one-below read.
     * The placed oak state is non-decaying (legacy metadata 4). Oak evidence:
     * development/research/source-evidence/20261119/block-a-nonfalling-water-oak-leaves/.
     */
    private static boolean isAdditionalDrySideBlocker(BlockState state) {
        return state.getFluidState().isEmpty()
            && (state.is(Blocks.GLASS) || STAINED_GLASS.contains(state.getBlock())
                || (state.is(Blocks.OAK_LEAVES) && state.getValue(LeavesBlock.PERSISTENT)));
    }

    private static int depth(BlockState state) {
        return state.getBlock() instanceof LiquidBlock && state.getFluidState().is(FluidTags.WATER)
            ? state.getValue(LiquidBlock.LEVEL) : -1;
    }
}
