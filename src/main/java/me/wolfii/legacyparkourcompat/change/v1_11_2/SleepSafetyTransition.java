package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerRestSafetyBehavior;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;

import java.util.function.BooleanSupplier;

@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class SleepSafetyTransition implements PlayerRestSafetyBehavior {
    @Override
    public boolean preventsRest(Monster monster, BooleanSupplier vanilla) {
        if (monster instanceof ZombifiedPiglin) {
            return true;
        }
        return vanilla.getAsBoolean();
    }
}
