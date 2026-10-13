package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.PassengerCrouchHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines 21-34.
 *   Source SHA-256: a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 654-660.
 *   serverAiStep; lines 609-618.
 *   isEffectiveAi; lines 486-488.
 *   Source SHA-256: 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2546.
 *   travel; lines 1983-2116.
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
 * 1.20.1/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines 21-34.
 *   Source SHA-256: a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0.
 * 1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 657-665.
 *   isCrouching; lines 603-605.
 *   isCrouching; lines 603-609.
 *   serverAiStep; lines 612-623.
 *   isEffectiveAi; lines 489-491.
 *   Source SHA-256: 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2.
 * 1.20.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   moveRelative; lines 1284-1301.
 *   isControlledByLocalInstance; lines 2909-2911.
 *   Source SHA-256: 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759.
 * 1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2526-2572.
 *   travel; lines 2004-2135.
 *   Source SHA-256: decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   travel; lines 1446-1473.
 *   updatePlayerPose; lines 372-400.
 *   Source SHA-256: 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac.
 * 1.20.1/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java:
 *   getSneakingSpeedBonus; lines 193-195.
 *   Source SHA-256: c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128.
 * 1.20.2/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines 21-34.
 *   Source SHA-256: a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0.
 * 1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 655-664.
 *   isCrouching; lines 601-603.
 *   isCrouching; lines 603-609.
 *   serverAiStep; lines 612-623.
 *   isEffectiveAi; lines 489-491.
 *   Source SHA-256: bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
 * 1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2526-2572.
 *   Source SHA-256: 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828.
 * 1.20.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   updatePlayerPose; lines 372-400.
 *   Source SHA-256: 25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c.
 * 1.20.2/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java:
 *   getSneakingSpeedBonus; lines 193-195.
 *   Source SHA-256: c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128.
 * 1.20.6/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines 21-34.
 *   Source SHA-256: a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0.
 * 1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 659-668.
 *   isMovingSlowly; lines 609-618.
 *   isEffectiveAi; lines 489-491.
 *   Source SHA-256: 6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 2104-2233.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * Bounded source decisions:
 *   Review 626fd4ffc3982169be5990949049ee0d2e3e1bb7; accepted for finding-only handoff.
 *   Reviewed snapshot 4ee5bbce85b193f972766c324c262e375fed9bd6; SHA-256 e1d51ee7f7395415dbe5d9d9692ea1f55cdd83cf0172de27c555e70c362a29d7.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class PassengerCrouch implements PassengerCrouchHook {
    @Override
    public boolean blocksCrouching(Player player, boolean vanilla) {
        return false;
    }
}
