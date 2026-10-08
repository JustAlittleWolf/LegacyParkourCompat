package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintJumpImpulseBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class SprintJumpImpulse implements SprintJumpImpulseBehavior {
    @Override
    public Vec3 impulse(LivingEntity entity, Vec3 vanilla) {
        float angle = entity.getYRot() * (float)(Math.PI / 180.0);
        return new Vec3(-LegacyTrig.sin(angle) * 0.2F, 0.0, LegacyTrig.cos(angle) * 0.2F);
    }
}
