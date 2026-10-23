package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fishing.FishingRetrievalImpulseHook;
import net.minecraft.world.phys.Vec3;

/**
 * 1.10, 1.10.1 and 1.10.2, Ornithe Feather Gen2 build 2:
 * FishingBobberEntity.pullHookedEntity adds the nested float-returning
 * MathHelper.sqrt lift. 1.11 (and 1.11.1/1.11.2), same mappings and member,
 * removes it. MathHelper.sqrt(double) casts Math.sqrt to float at both calls.
 * Native 26.2 unobfuscated FishingHook.pullEntity uses only delta.scale(0.1).
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class FishingRetrievalImpulse implements FishingRetrievalImpulseHook {
    @Override
    public Vec3 impulse(double dx, double dy, double dz, Vec3 nativeImpulse) {
        double distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
        return new Vec3(dx * 0.1, dy * 0.1 + (float) Math.sqrt(distance) * 0.08, dz * 0.1);
    }
}
