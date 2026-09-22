package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.NegligibleSpeedBehavior;
import net.minecraft.world.entity.Entity;

/** Through 1.21.4, each player velocity axis below 0.003 was zeroed separately. */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PerAxisNegligibleSpeed implements NegligibleSpeedBehavior {
    @Override
    public double threshold(Entity entity, double vanilla) {
        return 0.003;
    }
}
