package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.AbilityFlightGlideFlagEligibility;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.AbilityFlightGlideFlagHook;
import net.minecraft.world.entity.player.Player;

/**
 * Restores the later direct clear for local ability-flight travel. In 1.20.5
 * and 1.21.1 (Mojmap), {@code Player#travel(Vec3)} calls
 * {@code super.travel(Vec3)} before {@code setSharedFlag(7,false)}. In 26.2
 * (unobfuscated), {@code Player#travel(Vec3)} omits that post-travel clear;
 * its already-live flag path reaches {@code LivingEntity#travel(Vec3)} and
 * {@code LivingEntity#travelFallFlying(Vec3)}. The native V1_20_2 fence keeps
 * the unaccepted middle profiles unchanged, and later profiles have no
 * applicable provider.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class LateAbilityFlightGlideFlag implements AbilityFlightGlideFlagHook {
    @Override
    public boolean shouldClearAfterTravel(Player player) {
        return AbilityFlightGlideFlagEligibility.shouldClearAfterTravel(player);
    }
}
