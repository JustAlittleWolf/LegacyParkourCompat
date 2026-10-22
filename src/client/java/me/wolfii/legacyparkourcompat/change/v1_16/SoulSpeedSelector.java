package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.SoulSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedBlockHook;
import net.minecraft.world.entity.LivingEntity;

/** 1.16 Mojmap LivingEntity#onSoulSpeedBlock reads getBlockStateOn(). */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class SoulSpeedSelector implements SoulSpeedBlockHook {
    public SoulSpeedSelector() {
    }

    @Override
    public boolean onSoulSpeedBlock(LivingEntity entity) {
        return SoulSpeedBehavior.onLegacyOnBlock(entity);
    }
}
