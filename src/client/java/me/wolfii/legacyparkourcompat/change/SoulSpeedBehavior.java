package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedBlockHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;

/** Shared Soul Speed predicates and the base factor restored by the 1.16–1.20.6 sources. */
public final class SoulSpeedBehavior {
    private SoulSpeedBehavior() {
    }

    /**
     * 1.16 Mojmap Entity#getOnPos and LivingEntity#getBlockStateOn select
     * floor(position.y - 0.2F), falling back one block only for an air cell over
     * a fence, wall, or fence gate. 1.16.2 changes LivingEntity#onSoulSpeedBlock
     * to use Entity#getBlockPosBelowThatAffectsMyMovement instead.
     */
    public static boolean onLegacyOnBlock(LivingEntity entity) {
        BlockPos pos = BlockPos.containing(entity.getX(), entity.getY() - 0.2F, entity.getZ());
        BlockState state = entity.level().getBlockState(pos);
        if (state.isAir()) {
            BlockPos below = pos.below();
            BlockState belowState = entity.level().getBlockState(below);
            if (belowState.is(BlockTags.FENCES) || belowState.is(BlockTags.WALLS)
                || belowState.getBlock() instanceof FenceGateBlock) {
                pos = below;
            }
        }
        return entity.level().getBlockState(pos).is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    /** 1.16.2–1.19.4 Mojmap LivingEntity#onSoulSpeedBlock uses this exact support expression. */
    public static boolean onBoundingBoxSupportBlock(LivingEntity entity) {
        BlockPos pos = BlockPos.containing(
            entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ()
        );
        return entity.level().getBlockState(pos).is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    /** 1.20.1–1.20.6 Mojmap LivingEntity#onSoulSpeedBlock uses Entity#getOnPos(0.500001F). */
    public static boolean onModernSupportBlock(LivingEntity entity) {
        return entity.level().getBlockState(entity.getBlockPosBelowThatAffectsMyMovement())
            .is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    /**
     * 1.16 and 1.20.6 Mojmap LivingEntity#getBlockSpeedFactor return exactly 1.0F
     * when the ordered Soul Speed block and enchantment predicates pass, and
     * otherwise call Entity#getBlockSpeedFactor. 1.21 changes the LivingEntity
     * method to lerp the base value using MOVEMENT_EFFICIENCY. Recompute the base
     * value here so a false Soul Speed predicate does not retain that new lerp.
     */
    public static float historicalFactor(LivingEntity entity, float nativeFactor) {
        if (MovementRuntime.profile(entity).target().olderThan(ParkourVersion.V1_16)) {
            return nativeFactor;
        }

        SoulSpeedBlockHook blockHook = MovementRuntime.find(SoulSpeedBlockHook.class, entity)
            .orElseThrow(() -> new IllegalStateException("Soul Speed block selector is missing from the active profile"));
        if (blockHook.onSoulSpeedBlock(entity)
            && EnchantmentHelper.getEnchantmentLevel(
                BuiltInRegistries.ENCHANTMENT.get(Enchantments.SOUL_SPEED)
                    .orElseThrow(() -> new IllegalStateException("Soul Speed enchantment is missing")),
                entity
            ) > 0) {
            return 1.0F;
        }
        return historicalBaseFactor(entity);
    }

    private static float historicalBaseFactor(LivingEntity entity) {
        BlockState state = entity.level().getBlockState(entity.blockPosition());
        float factor = state.getBlock().getSpeedFactor();
        if (!state.is(Blocks.WATER) && !state.is(Blocks.BUBBLE_COLUMN)) {
            return factor == 1.0F
                ? entity.level().getBlockState(entity.getBlockPosBelowThatAffectsMyMovement()).getBlock().getSpeedFactor()
                : factor;
        }
        return factor;
    }
}
