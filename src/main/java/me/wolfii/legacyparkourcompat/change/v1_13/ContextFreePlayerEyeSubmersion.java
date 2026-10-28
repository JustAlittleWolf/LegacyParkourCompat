package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.fluid.PlayerEyeWaterMath;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeSubmersionHook;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

/**
 * Keeps the 1.13.2 player's eye test context-free. 1.13.2 Ornithe Feather
 * {@code Entity#isSubmergedIn} rejects a boat, checks the fluid tag, then tests
 * {@code d < blockY + (state.getLevel() / 9.0F + 0.11111111F)}. In 1.14
 * Ornithe Feather {@code Entity#isSubmergedIn} calls
 * {@code FluidState#getHeight(BlockView, BlockPos)}, whose flowing-fluid
 * implementation returns full-block height when the same fluid is directly
 * above. The 26.2 unobfuscated {@code EntityFluidInteraction#update} compares
 * the sampled eye cell against its contextual top, while
 * {@code FluidState#getOwnHeight} delegates to the context-free amount/9
 * calculation. Preserve the strict comparison and float addition at that
 * sampled eye block.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class ContextFreePlayerEyeSubmersion implements PlayerEyeSubmersionHook {
    public ContextFreePlayerEyeSubmersion() {
    }

    @Override
    public Optional<Boolean> isSubmerged(
        FluidState eyeFluidState,
        boolean matchesFluidTag,
        boolean mountedInBoat,
        double eyeY,
        int eyeBlockY
    ) {
        if (mountedInBoat) {
            return Optional.of(false);
        }
        if (!matchesFluidTag) {
            return Optional.of(false);
        }
        return Optional.of(PlayerEyeWaterMath.isSubmerged(eyeY, eyeBlockY, eyeFluidState.getOwnHeight()));
    }
}
