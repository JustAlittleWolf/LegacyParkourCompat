package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.LoadedFinalBoxContactScan;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.HoneyContactTimingHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mojmap 1.19 through 1.20.6 Entity.move runs checkInsideBlocks after
 * resolving the move and before getBlockSpeedFactor; 1.19.3 reduces the
 * six-edge inset. 1.21 and 1.21.1 use the separate final-box traversal.
 * Mojmap 1.21.2 LivingEntity.aiStep defers the block effects until after
 * travel, and HoneyBlock compensates for the earlier gravity and drag.
 * Replay only the Honey response at the old site, using the independently
 * resolved inset and the existing HoneyBlock slide gate and velocity hooks.
 *
 * Sources: 1.19.2 and 1.19.3 Mojmap Entity.move/checkInsideBlocks,
 * HoneyBlock.entityInside/doSlideMovement and LivingEntity.travel;
 * 1.20.5 and 1.20.6 Mojmap Entity.move/checkInsideBlocks and
 * HoneyBlock.entityInside/doSlideMovement;
 * 1.21.2 Mojmap LivingEntity.tick and HoneyBlock.getOldDeltaY/getNewDeltaY;
 * 26.2 unobfuscated LivingEntity.aiStep/travelInAir,
 * Entity.applyEffectsFromBlocks and HoneyBlock.entityInside/doSlideMovement.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class HoneyContactTiming implements HoneyContactTimingHook {
    @Override
    public boolean replay(LocalPlayer player, double inset) {
        Level level = player.level();
        return LoadedFinalBoxContactScan.visit(player, inset, pos -> {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.HONEY_BLOCK)) {
                state.entityInside(level, pos, player, InsideBlockEffectApplier.NOOP, true);
            }
        });
    }
}
