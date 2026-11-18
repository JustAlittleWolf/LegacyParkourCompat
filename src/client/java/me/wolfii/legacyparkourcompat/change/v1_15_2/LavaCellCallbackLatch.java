package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.LavaPresenceScan;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.fluid.LavaPresenceLatchContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaPresenceHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.LiquidBlockAccessor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.LiquidBlock;
import java.util.Optional;

/**
 * In Mojmap 1.15, 1.15.1 and 1.15.2, Entity.move clears the lava flag just
 * before Entity.checkInsideBlocks. LiquidBlock.entityInside then sets the flag
 * for any visited block whose configured fluid is lava, independent of the
 * stored level or fluid surface. Entity.isInLava reads the retained flag.
 * 26.2 unobfuscated Entity.isInLava instead checks the current fluid tracker.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class LavaCellCallbackLatch implements LavaPresenceHook {
    @Override
    public Optional<Boolean> isInLava(Entity entity, Optional<LavaMaterialScanUpperBoundHook> scanUpperBound) {
        if (!(entity instanceof LocalPlayer) || !entity.level().isClientSide()) {
            return Optional.empty();
        }
        return Optional.of(latch(entity).legacyparkourcompat$isLavaPresenceLatched());
    }

    @Override
    public void afterMove(Entity entity) {
        if (!(entity instanceof LocalPlayer) || !entity.level().isClientSide() || entity.noPhysics) {
            return;
        }

        LavaPresenceLatchContext context = latch(entity);
        context.legacyparkourcompat$setLavaPresenceLatched(false);
        if (LavaPresenceScan.containsHistoricalCallbackCell(entity, state ->
            state.getBlock() instanceof LiquidBlock block
                && ((LiquidBlockAccessor) block).legacyparkourcompat$configuredFluid().is(FluidTags.LAVA)
        )) {
            context.legacyparkourcompat$setLavaPresenceLatched(true);
        }
    }

    private static LavaPresenceLatchContext latch(Entity entity) {
        if (entity instanceof LavaPresenceLatchContext context) {
            return context;
        }
        throw new IllegalStateException("Local player is missing lava-presence latch state");
    }
}
