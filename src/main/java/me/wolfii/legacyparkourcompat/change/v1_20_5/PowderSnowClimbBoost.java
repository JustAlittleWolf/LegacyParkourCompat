package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PowderSnowClimbBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class PowderSnowClimbBoost implements PowderSnowClimbBehavior {
    @Override
    public boolean wasInPowderSnowForBoost(LivingEntity entity, boolean vanilla) {
        return entity.getInBlockState().is(Blocks.POWDER_SNOW);
    }
}
