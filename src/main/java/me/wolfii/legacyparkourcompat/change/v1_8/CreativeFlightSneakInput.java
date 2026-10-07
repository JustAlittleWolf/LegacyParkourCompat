package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import net.minecraft.world.phys.Vec2;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class CreativeFlightSneakInput implements ClientInputBehavior {
    @Override
    public Vec2 modify(
        Vec2 input,
        boolean usingItem,
        boolean movingSlowly,
        float sneakingSpeed,
        boolean flyingSneak
    ) {
        return flyingSneak ? input.scale(0.3F) : input;
    }
}
