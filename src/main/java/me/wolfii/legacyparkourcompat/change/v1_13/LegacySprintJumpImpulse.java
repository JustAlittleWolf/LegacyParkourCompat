package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.HistoricalTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintJumpImpulseBehavior;
import net.minecraft.world.phys.Vec3;

/** Old sprint jumps multiply float sine and cosine by 0.2F before adding velocity. */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class LegacySprintJumpImpulse implements SprintJumpImpulseBehavior {
    @Override
    public Vec3 impulse(float yaw, Vec3 vanilla) {
        float angle = yaw * (float) (Math.PI / 180.0);
        return new Vec3(-HistoricalTrig.sin(angle) * 0.2F, 0.0, HistoricalTrig.cos(angle) * 0.2F);
    }
}
