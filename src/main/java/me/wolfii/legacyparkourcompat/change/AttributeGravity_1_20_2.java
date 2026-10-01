package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.GravityBehavior;
import net.minecraft.world.entity.Entity;

@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class AttributeGravity_1_20_2 implements GravityBehavior {
    @Override
    public double gravity(Entity entity, double vanilla) {
        return entity.isNoGravity() ? 0.0 : 0.08;
    }
}
