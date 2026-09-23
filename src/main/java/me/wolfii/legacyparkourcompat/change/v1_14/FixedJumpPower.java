package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.world.entity.LivingEntity;

/** 1.14 does not multiply jump power by the supporting block's jump factor. */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class FixedJumpPower extends FloatJumpThrough116 {
    @Override
    protected float basePower(LivingEntity entity) {
        return 0.42F;
    }
}
