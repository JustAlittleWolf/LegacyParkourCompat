package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.SoulSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedBlockHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedFrictionHook;
import net.minecraft.world.entity.LivingEntity;

/** 1.20.6 Mojmap LivingEntity#onSoulSpeedBlock/getBlockSpeedFactor; 1.21 changes friction to MOVEMENT_EFFICIENCY. */
public final class SoulSpeedChanges {
    private SoulSpeedChanges() {
    }

    @MovementChange(emulates = ParkourVersion.V1_20_5)
    public static final class SelectorAndFriction implements SoulSpeedBlockHook, SoulSpeedFrictionHook {
        public SelectorAndFriction() {
        }

        @Override
        public boolean onSoulSpeedBlock(LivingEntity entity) {
            return SoulSpeedBehavior.onModernSupportBlock(entity);
        }

        @Override
        public float blockSpeedFactor(LivingEntity entity, float nativeFactor) {
            return SoulSpeedBehavior.historicalFactor(entity, nativeFactor);
        }
    }
}
