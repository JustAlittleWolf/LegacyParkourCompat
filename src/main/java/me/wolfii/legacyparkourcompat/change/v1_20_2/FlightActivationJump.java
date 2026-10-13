package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FlightActivationJumpHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 720-728.
 *   Source SHA-256: 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
 * 1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 726-732.
 *   Source SHA-256: bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5.
 * 1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround; lines 2008-2017.
 *   aiStep; lines 2571-2587.
 *   Source SHA-256: f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d.
 * 1.20.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   jumpFromGround; lines 1440-1447.
 *   Source SHA-256: 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd.
 * 1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 728-739.
 *   aiStep; lines 729-739.
 *   Source SHA-256: 6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround; lines 2069-2080.
 *   jumpFromGround; lines 2069-2081.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:
 *   jumpFromGround; lines 1462-1468.
 *   Source SHA-256: 785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2.
 * Bounded source decisions:
 *   Review 581b5bd2cb44981c4889eaf05ea739134e332b87; ACCEPT, finding snapshot only.
 *   Reviewed snapshot 1985fa829eb597acbc23685935c5f6929c078f78; SHA-256 eb15d0f72764cf783cb0ac260d28f8a31827f3bb407015dd66f1601ca3ad0fda.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class FlightActivationJump implements FlightActivationJumpHook {
    @Override
    public void onFlightActivated(Player player, VanillaCall vanilla) {
        // Flight activation did not jump in 1.20.2 and earlier.
    }
}
