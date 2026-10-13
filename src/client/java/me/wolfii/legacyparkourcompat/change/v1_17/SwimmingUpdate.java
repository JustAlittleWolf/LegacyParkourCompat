package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SwimmingUpdateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Preserves swimming entry through 1.17 without the 1.17.1
 * block-fluid gate; an existing swimmer keeps the sprint/water/passenger predicate.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.16.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateSwimming, isUnderWater.
 * 1.16.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateSwimming, isUnderWater.
 * 1.17.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateSwimming adds the block-position water-fluid check on entry.
 * 1.17/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateSwimming still lacks that check; 1.17.1 is the first changed stable release.
 * 1.16.5/mojmap/net/minecraft/world/entity/player/Player.java:
 *   updateSwimming keeps the flying-player veto before calling Entity.
 * 1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   isUnderWater returns the retained wasUnderwater flag.
 * 26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java:
 *   isUnderWater retains the same local-player flag predicate.
 * 26.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   updateSwimming retains the flying-player veto before calling Entity.
 * The superclass hook runs after the native Player flying veto. Exact 1.17 and
 * 1.17.1 bodies establish the entry boundary; older mapping-artifact acceptance
 * remains separate. Profiles before swimming existed retain native behavior.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class SwimmingUpdate implements SwimmingUpdateHook {
    @Override
    public boolean isSwimmingAfterUpdate(Player player, ParkourVersion selected) {
        if (player.isSwimming()) {
            return player.isSprinting() && player.isInWater() && !player.isPassenger();
        }
        return player.isSprinting() && player.isUnderWater() && !player.isPassenger();
    }
}
