package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeSubmersionHook;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

/** Leaves earlier profiles native until an earlier eye-submersion boundary is source-confirmed. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeBeforeEyeSubmersion implements PlayerEyeSubmersionHook {
    public NativeBeforeEyeSubmersion() {
    }

    @Override
    public Optional<Boolean> isSubmerged(
        FluidState eyeFluidState,
        boolean matchesFluidTag,
        boolean mountedInBoat,
        double eyeY,
        int eyeBlockY
    ) {
        return Optional.empty();
    }
}
