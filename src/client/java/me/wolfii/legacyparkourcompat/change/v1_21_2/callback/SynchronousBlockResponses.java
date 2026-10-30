package me.wolfii.legacyparkourcompat.change.v1_21_2.callback;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.callback.SweptBlockResponseHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.AbstractCauldronBlockAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;

/**
 * Callback-body response only; this does not implement the historical scan or its eligibility.
 * Sources: 1.21.2 and 1.21.3 Mojmap net.minecraft.world.level.block.{HoneyBlock,WebBlock,
 * BaseFireBlock,LavaCauldronBlock,LayeredCauldronBlock,PowderSnowBlock}.entityInside and
 * net.minecraft.world.entity.Entity.{lavaHurt,makeStuckInBlock}; 26.2 unobfuscated uses the
 * same callback members on those block classes and Entity. The paired old callback sources
 * are byte-identical for the accepted families.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class SynchronousBlockResponses implements SweptBlockResponseHook {
    @Override
    public boolean apply(BlockState state, Level level, BlockPos pos, Entity entity, VanillaCall nativeCallback) {
        var block = state.getBlock();
        if (block instanceof HoneyBlock || block instanceof WebBlock) {
            // HoneyBlock.entityInside and WebBlock.entityInside are callback-body equivalent
            // in 1.21.2/1.21.3 Mojmap and 26.2 unobfuscated; preserve their immediate native body.
            nativeCallback.run();
            return true;
        }

        if (block instanceof BaseFireBlock) {
            applyFire(level, entity);
            return true;
        }

        if (block instanceof LavaCauldronBlock cauldron) {
            applyLavaCauldron(cauldron, state, pos, entity);
            return true;
        }

        if (block instanceof LayeredCauldronBlock) {
            // Historical LayeredCauldronBlock.entityInside is ServerLevel-only, so this
            // client-player movement operation has no callback response for the family.
            return true;
        }

        if (block instanceof PowderSnowBlock) {
            applyPowderSnow(state, level, pos, entity);
            return true;
        }

        return false;
    }

    private static void applyFire(Level level, Entity entity) {
        // 1.21.2/1.21.3 Mojmap BaseFireBlock.entityInside updates the timer and ignites inline.
        // Preserve that movement response; its hurt call is outside the project's damage scope.
        if (!entity.fireImmune()) {
            if (entity.getRemainingFireTicks() < 0) {
                entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 1);
            } else if (entity instanceof ServerPlayer) {
                int increment = level.getRandom().nextInt(1, 3);
                entity.setRemainingFireTicks(entity.getRemainingFireTicks() + increment);
            }

            if (entity.getRemainingFireTicks() >= 0) {
                entity.igniteForSeconds(8.0F);
            }
        }
    }

    private static void applyPowderSnow(BlockState state, Level level, BlockPos pos, Entity entity) {
        // 1.21.2/1.21.3 Mojmap and 26.2 unobfuscated PowderSnowBlock.entityInside; preserve
        // its inline stuck response, particle gate/order and powder-snow entity flag.
        if (!(entity instanceof LivingEntity) || entity.getInBlockState().is(state.getBlock())) {
            entity.makeStuckInBlock(state, new Vec3(0.9F, 1.5, 0.9));
            if (level.isClientSide()) {
                RandomSource random = level.getRandom();
                boolean movedHorizontally = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (movedHorizontally && random.nextBoolean()) {
                    level.addParticle(
                        ParticleTypes.SNOWFLAKE,
                        entity.getX(),
                        pos.getY() + 1,
                        entity.getZ(),
                        Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F,
                        0.05F,
                        Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F
                    );
                }
            }
        }

        entity.setIsInPowderSnow(true);
    }

    private static void applyLavaCauldron(
        AbstractCauldronBlock cauldron,
        BlockState state,
        BlockPos pos,
        Entity entity
    ) {
        // 1.21.2/1.21.3 Mojmap LavaCauldronBlock.entityInside first applies
        // AbstractCauldronBlock.isEntityInsideContent, then Entity.lavaHurt. Keep its
        // movement-relevant ignition while excluding its damage and sound behavior.
        double contentHeight = ((AbstractCauldronBlockAccessor) cauldron).legacyParkourCompat$contentHeight(state);
        if (entity.getY() < pos.getY() + contentHeight
            && entity.getBoundingBox().maxY > pos.getY() + 0.25
            && !entity.fireImmune()) {
            entity.igniteForSeconds(15.0F);
        }
    }
}
