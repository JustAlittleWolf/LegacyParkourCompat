package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.InputVectorHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * Ornithe Feather 1.13.2 Entity#updateVelocity performs normalization,
 * scaling, and yaw rotation with float components. Mojmap 1.14.4
 * Entity#getInputVector and unobfuscated 26.2 Entity#getInputVector use double
 * Vec3 arithmetic and the 1.0E-7 squared-length cutoff instead. The existing
 * InputAccelerationHook handles supported historical input before this seam;
 * this hook covers only ordinary diagonal keyboard input that reaches the
 * generic helper. Inputs below the historical 1.0E-4F cutoff retain native
 * behavior because their ordinary-tick reachability is not established.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class InputVector implements InputVectorHook {
    @Override
    public Vec3 inputVector(Vec3 input, float speed, float yaw, VanillaFn<Vec3> vanilla) {
        float sideways = (float) input.x;
        float forward = (float) input.z;
        float upward = (float) input.y;
        float length = sideways * sideways + forward * forward + upward * upward;
        if (length < 1.0E-4F) {
            return vanilla.get();
        }

        length = Mth.sqrt(length);
        if (length < 1.0F) {
            length = 1.0F;
        }
        length = speed / length;
        sideways *= length;
        forward *= length;
        upward *= length;
        float sin = Mth.sin(yaw * (float) (Math.PI / 180.0));
        float cos = Mth.cos(yaw * (float) (Math.PI / 180.0));
        return new Vec3(sideways * cos - forward * sin, upward, forward * cos + sideways * sin);
    }
}
