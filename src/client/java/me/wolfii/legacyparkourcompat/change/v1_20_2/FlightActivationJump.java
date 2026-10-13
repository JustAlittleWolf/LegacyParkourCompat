package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FlightActivationJumpHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * Stable cutover: 1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java,
 * aiStep, toggles flight without calling jumpFromGround;
 * 1.20.5/mojmap/net/minecraft/client/player/LocalPlayer.java, aiStep,
 * calls jumpFromGround when flight becomes active while grounded.
 * V1_20_2 includes 1.20.3 and 1.20.4, so this resolver entry covers the
 * entire preceding profile group. The first changed stable release is 1.20.5.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround.
 *   aiStep.
 * 1.20.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   jumpFromGround.
 * 1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround.
 *   jumpFromGround.
 * 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:
 *   jumpFromGround.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class FlightActivationJump implements FlightActivationJumpHook {
    @Override
    public void onFlightActivated(Player player, VanillaCall vanilla) {
        // Flight activation did not jump through 1.20.4.
    }
}
