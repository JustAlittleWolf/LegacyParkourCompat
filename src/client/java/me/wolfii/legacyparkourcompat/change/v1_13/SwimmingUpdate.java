package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SwimmingUpdateHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Preserves the 1.13 already-swimming flag under the accepted source-water /
 * full-stone-roof fixture. In Minecraft 1.13, Ornithe Feather Gen2
 * ({@code net.ornithemc:feather-gen2:1.13+build.2}),
 * {@code net.minecraft.entity.living.player.PlayerEntity#updateSwimming} calls
 * {@code net.minecraft.entity.Entity#updateSwimming} only when the old
 * feet/head clearance passes; its lower
 * {@code PlayerEntity#shouldSuffocate(BlockPos)} and upper material checks are
 * distinct from modern collision-shape or fluid predicates. In Minecraft
 * 1.13.1, Feather Gen2
 * ({@code net.ornithemc:feather-gen2:1.13.1+build.2}), the same
 * {@code PlayerEntity#updateSwimming} calls {@code Entity#updateSwimming}
 * unconditionally in its non-flying arm. The accepted 26.2 unobfuscated
 * {@code net.minecraft.world.entity.player.Player#updateSwimming} also calls
 * its superclass unconditionally in that arm. Static bytecode inspection of
 * the product target confirms that invocation's symbolic owner is
 * {@code net.minecraft.world.entity.Avatar#updateSwimming}.
 *
 * <p>This provider suppresses only that superclass delegation for the exact
 * accepted local fixture. It uses the floored feet block's exact default
 * water state and the exact default stone state immediately above it. It does
 * not generalize to other materials, shapes, fluids, or remote players.
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
        BlockState feetState = player.level().getBlockState(feet);
        if (!feetState.equals(Blocks.WATER.defaultBlockState())) {
            return true;
        }

        return !player.level().getBlockState(feet.above()).equals(Blocks.STONE.defaultBlockState());
    }
}
