package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSneakImpulseHook;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * 1.12.2 (Ornithe Feather), LocalClientPlayerEntity.mobTick has no water-sneak
 * impulse. 1.13 (Ornithe Feather), LocalClientPlayerEntity.mobTick calls
 * LivingEntity.knockDownwards. Keep native arithmetic when older profiles
 * reach the current impulse through their existing fallback.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterSneakImpulseFence implements WaterSneakImpulseHook {
    @Override
    public Optional<Vec3> applyImpulse(Vec3 movement) {
        return Optional.empty();
    }
}
