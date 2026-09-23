package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import net.minecraft.world.phys.Vec2;

/** Early keyboard input applies sneak slowdown before item-use slowdown. */
@MovementChange(emulates = ParkourVersion.V1_8)
public class EarlyKeyboardInput implements ClientInputBehavior {
    @Override
    public Vec2 modify(Vec2 raw, Vec2 vanilla, boolean usingItem, boolean movingSlowly, float sneakingSpeed, boolean flyingSneak) {
        if (raw.lengthSquared() == 0.0F && vanilla.lengthSquared() != 0.0F) {
            return vanilla;
        }
        float sideways = raw.x;
        float forward = raw.y;
        if (movingSlowly) {
            sideways *= sneakingSpeed;
            forward *= sneakingSpeed;
        }
        if (usingItem) {
            sideways *= 0.2F;
            forward *= 0.2F;
        }
        Vec2 prepared = afterSlowdown(sideways, forward, flyingSneak);
        return new Vec2(prepared.x * 0.98F, prepared.y * 0.98F);
    }

    protected Vec2 afterSlowdown(float sideways, float forward, boolean flyingSneak) {
        return new Vec2(sideways, forward);
    }
}
