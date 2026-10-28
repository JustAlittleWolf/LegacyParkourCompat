package me.wolfii.legacyparkourcompat.change.v1_13.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.fluid.PlayerEyeWaterMath;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeWaterQueryHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

/** Restores the 1.13 cached underwater producer without adopting the modern body-scan admission gate. */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class PlayerEyeWaterQuery implements PlayerEyeWaterQueryHook {
    public PlayerEyeWaterQuery() {
    }

    /**
     * Exact source comparison: 1.13.2 Feather {@code PlayerEntity#tick/updateWaterSubmersion} runs before
     * {@code Entity#tick} and calls {@code Entity#isSubmergedIn}; that method samples
     * {@code World#getFluidState} at the eye position and uses the tagged fluid's context-free
     * {@code getLevel() / 9.0F} height with the strict 1/9F boundary, after rejecting boat passengers.
     * The shared {@link PlayerEyeWaterMath} keeps the same float operation order as the separate
     * {@link me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeSubmersionHook} operation.
     * In 26.2 unobfuscated source, {@code Player#tick/updateIsUnderwater} reads the eye tracker before
     * {@code Entity#baseTick} calls {@code updateFluidInteraction}, whose {@code hasFluidAndLoaded} padded
     * body-scan gate can suppress refreshing that tracker.
     */
    @Override
    public Optional<Boolean> queryEyeWater(Player player) {
        var vehicle = player.getVehicle();
        if (vehicle instanceof AbstractBoat && !(vehicle instanceof Boat)) {
            // Chest boats and rafts have no 1.13.2 counterpart; retain their native tracker result.
            return Optional.empty();
        }
        if (vehicle instanceof Boat) {
            return Optional.of(false);
        }

        double eyeY = player.getY() + player.getEyeHeight();
        BlockPos eyePos = BlockPos.containing(player.getX(), eyeY, player.getZ());
        FluidState fluidState = player.level().getFluidState(eyePos);
        return Optional.of(fluidState.is(FluidTags.WATER)
            && PlayerEyeWaterMath.isSubmerged(eyeY, eyePos.getY(), fluidState.getOwnHeight()));
    }
}
