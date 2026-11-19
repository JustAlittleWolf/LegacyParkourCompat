package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSneakImpulseHook;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * 1.13, 1.13.1 and 1.13.2 (Ornithe Feather), LocalClientPlayerEntity.mobTick
 * calls LivingEntity.knockDownwards, which subtracts 0.04F from Y alone.
 * In 1.14 (Ornithe Feather), LivingEntity.knockDownwards switches to Vec3d.add.
 * Adding +0.0 to X and Z clears negative zero. 26.2 (unobfuscated)
 * LivingEntity.goDownInWater uses the same component-wise addition.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class WaterSneakImpulse implements WaterSneakImpulseHook {
    @Override
    public Optional<Vec3> applyImpulse(Vec3 movement) {
        return Optional.of(new Vec3(movement.x, movement.y - 0.04F, movement.z));
    }
}
