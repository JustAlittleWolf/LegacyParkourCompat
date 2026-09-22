package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_8.EarlyKeyboardInput;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.world.phys.Vec2;

/** 1.9–1.14 cancel horizontal sneak slowdown during controlled flight. */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class FlyingSneakInput extends EarlyKeyboardInput {
    @Override
    protected Vec2 afterSlowdown(float sideways, float forward, boolean flyingSneak) {
        if (!flyingSneak) {
            return super.afterSlowdown(sideways, forward, false);
        }
        return new Vec2((float) (sideways / 0.3), (float) (forward / 0.3));
    }
}
