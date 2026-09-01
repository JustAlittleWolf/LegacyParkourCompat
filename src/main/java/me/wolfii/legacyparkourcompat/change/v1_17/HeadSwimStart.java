package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import net.minecraft.world.entity.player.Player;

/**
 * Until 1.17, swim-start used head-in-water ({@code isUnderWater}) without
 * requiring the block at {@code blockPosition} to be water. 1.17 added that
 * check so a waterlogged ceiling no longer flickers swim. 1.12 and earlier
 * keep {@link me.wolfii.legacyparkourcompat.change.v1_12.NoSwimming}.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class HeadSwimStart implements SwimmingBehavior {
    @Override
    public void updateSwimming(Player player, VanillaCall vanilla) {
        if (player.getAbilities().flying) {
            player.setSwimming(false);
            return;
        }
        if (player.isSwimming()) {
            player.setSwimming(player.isSprinting() && player.isInWater() && !player.isPassenger());
        } else {
            player.setSwimming(player.isSprinting() && player.isUnderWater() && !player.isPassenger());
        }
    }
}
