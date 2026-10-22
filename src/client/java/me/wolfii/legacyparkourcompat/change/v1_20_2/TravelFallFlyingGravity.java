package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.TravelFallFlyingGravityHook;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

/**
 * 1.20.4 Mojmap LivingEntity.travel uses 0.08 gravity, reduced to 0.01 when
 * descending with Slow Falling; its fall-flying branch uses that value in its
 * vertical update without the later NoGravity/attribute gravity source. 1.20.5
 * Mojmap Entity.getGravity and LivingEntity.travel introduce that gravity input
 * and cap it with Math.min(gravity, 0.01) before the same branch; 1.20.6 retains
 * that path. Water and ordinary falling have separate gravity gates and are
 * not changed here.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class TravelFallFlyingGravity implements TravelFallFlyingGravityHook {
    @Override
    public double gravity(LivingEntity entity) {
        boolean isFalling = entity.getDeltaMovement().y <= 0.0;
        return isFalling && entity.hasEffect(MobEffects.SLOW_FALLING) ? 0.01 : 0.08;
    }
}
