package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import net.minecraft.world.phys.Vec2;

/** Through 1.21.4, raw keyboard axes were scaled without square adjustment. */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PreSquareInput implements ClientInputBehavior {
    @Override
    public Vec2 modify(Vec2 raw, Vec2 vanilla, boolean usingItem, boolean movingSlowly, float sneakingSpeed, boolean flyingSneak) {
        if (raw.lengthSquared() == 0.0F && vanilla.lengthSquared() != 0.0F) {
            return vanilla;
        }
        float sideways = raw.x;
        float forward = raw.y;
        if (usingItem) {
            sideways *= 0.2F;
            forward *= 0.2F;
        }
        if (movingSlowly) {
            sideways *= sneakingSpeed;
            forward *= sneakingSpeed;
        }
        return new Vec2(sideways * 0.98F, forward * 0.98F);
    }
}
