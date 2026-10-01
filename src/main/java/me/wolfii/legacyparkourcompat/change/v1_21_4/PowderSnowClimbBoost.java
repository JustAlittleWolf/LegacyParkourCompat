package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PowderSnowClimbBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;


@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PowderSnowClimbBoost implements PowderSnowClimbBehavior {
    @Override
    public boolean wasInPowderSnowForBoost(LivingEntity entity, me.wolfii.legacyparkourcompat.api.ParkourVersion selected, boolean vanilla) {
        if (selected.olderThan(ParkourVersion.V1_17)) return vanilla;
        return entity.getInBlockState().is(Blocks.POWDER_SNOW);
    }
}
