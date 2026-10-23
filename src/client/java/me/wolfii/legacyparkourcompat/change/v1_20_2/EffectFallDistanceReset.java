package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Keeps the native pre-travel reset enabled for 1.20.2-era profiles. Mojmap
 * LivingEntity.aiStep resets fall distance before travel when either effect is
 * active; this timing also appears in 1.20.6 and 26.2. It follows the 1.20
 * cutover from the older effect-specific resets in LivingEntity.travel.
 *
 * Sources (paths relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java: travel.
 * 1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java: aiStep.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java: aiStep.
 * 26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java: aiStep.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class EffectFallDistanceReset implements EffectFallDistanceResetHook {
    @Override
    public boolean resetBeforeTravel(LivingEntity entity, boolean vanilla) {
        return vanilla;
    }
}
