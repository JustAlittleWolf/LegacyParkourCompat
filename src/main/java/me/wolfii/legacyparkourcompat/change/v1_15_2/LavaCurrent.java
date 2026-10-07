package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaCurrentBehavior;
import net.minecraft.world.entity.Entity;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class LavaCurrent implements LavaCurrentBehavior {
    @Override
    public boolean allowLavaCurrent(Entity entity, boolean vanilla) {
        return false;
    }
}
