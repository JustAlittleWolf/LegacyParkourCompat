package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.PowderSnowFreezeSlowdownHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

/** 1.16.5 Mojmap LivingEntity.aiStep has no local Frost update, introduced in 1.17 Mojmap. */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class NativePowderSnowFreezeSlowdown implements PowderSnowFreezeSlowdownHook {
    @Override
    public void recompute(Player player, VanillaFn<BlockState> frostSample) {
        // Retain the native client path.
    }
}
