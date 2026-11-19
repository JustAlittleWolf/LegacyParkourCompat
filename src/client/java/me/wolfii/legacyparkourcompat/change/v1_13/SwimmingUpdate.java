package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SwimmingUpdateHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

/**
 * Preserves the 1.13 already-swimming flag in water under a full stone roof.
 * In Minecraft 1.13, Ornithe Feather Gen2
 * ({@code net.ornithemc:feather-gen2:1.13+build.2}),
 * {@code net.minecraft.entity.living.player.PlayerEntity#updateSwimming} calls
 * {@code net.minecraft.entity.Entity#updateSwimming} for an already-swimming
 * player only when the feet/head clearance passes. {@code Block#init}
 * registers water with {@code Material.WATER}, so {@code Block#isSolid}
 * is false for every {@code LiquidBlock#LEVEL} of water. Full stone above
 * the player fails {@code PlayerEntity#doesNotSuffocate} for every water level.
 * In Minecraft
 * 1.13.1, Feather Gen2
 * ({@code net.ornithemc:feather-gen2:1.13.1+build.2}), the same
 * {@code PlayerEntity#updateSwimming} calls {@code Entity#updateSwimming}
 * unconditionally in its non-flying arm. The 26.2 unobfuscated
 * {@code net.minecraft.world.entity.player.Player#updateSwimming} also calls
 * its superclass unconditionally in that arm.
 *
 * <p>The provider covers the local water-block and default-stone fixture,
 * including source, flowing and falling water.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SwimmingUpdate implements SwimmingUpdateHook {
    @Override
    public boolean shouldDelegate(Player player) {
        if (!(player instanceof LocalPlayer)
            || !player.level().isClientSide()
            || player.getAbilities().flying
            || !player.isSwimming()
            || player.isPassenger()) {
            return true;
        }

        BlockPos feet = player.blockPosition();
        if (!player.level().getBlockState(feet).is(Blocks.WATER)) {
            return true;
        }

        return !player.level().getBlockState(feet.above()).equals(Blocks.STONE.defaultBlockState());
    }
}
