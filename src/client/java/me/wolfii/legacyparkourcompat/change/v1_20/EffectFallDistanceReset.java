package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * At the 1.20 boundary, preserve the native pre-travel reset for either effect.
 * Mojmap 1.20 LivingEntity.aiStep contains that reset, unlike 1.19.4
 * LivingEntity.travel, which resets only in the downward slow-falling and
 * ordinary non-fluid levitation branches. The same aiStep reset remains in
 * 1.20.6 and 26.2.
 *
 * Sources (paths relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java: travel.
 * 1.20/mojmap/net/minecraft/world/entity/LivingEntity.java: aiStep.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java: aiStep.
 * 26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java: aiStep.
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class EffectFallDistanceReset implements EffectFallDistanceResetHook {
    @Override
    public boolean resetBeforeTravel(LivingEntity entity, boolean vanilla) {
        return vanilla;
    }
}
