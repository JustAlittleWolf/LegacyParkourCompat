package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FlightActivationJumpHook;
import net.minecraft.world.entity.player.Player;

/**
 * Minecraft 1.19.4, 1.20.2, 1.20.3 and 1.20.4 (Mojmap),
 * net.minecraft.client.player.LocalPlayer#aiStep, toggle creative flight in the
 * double-jump branch, send abilities and reset jumpTriggerTime without calling
 * jumpFromGround. Minecraft 1.20.5 and 1.20.6 (Mojmap), same class/member,
 * insert that call when the toggled flying value and onGround are true, before
 * sending abilities. Minecraft 26.2 (unobfuscated), same class/member, retains
 * the inserted call.
 *
 * Suppress only that call through the existing flight-activation operation.
 * Enclosing flight/input eligibility, ability updates and subsequent vertical
 * flight input/travel remain at their original execution points. Independent
 * hooks continue to resolve other historical flight gates. V1_20_2 includes
 * 1.20.3 and 1.20.4, immediately before the first changed stable release 1.20.5;
 * earlier profile coverage is retained. Source correspondence does not
 * establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class FlightActivationJump implements FlightActivationJumpHook {
    @Override
    public void onFlightActivated(Player player, VanillaCall vanilla) {
        // Flight activation did not jump through 1.20.4.
    }
}
