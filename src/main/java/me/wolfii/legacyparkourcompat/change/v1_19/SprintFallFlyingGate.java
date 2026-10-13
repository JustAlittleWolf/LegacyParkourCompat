package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintFallFlyingGateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 698-705.
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   updateFallFlying; lines 2590-2612.
 *   travel; lines 2019-2049.
 *   getFluidFallingAdjustedMovement; lines 2174-2186.
 *   Source SHA-256: db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 709-717.
 *   Source SHA-256: 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479.
 * 1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   setSprinting; lines 1964-1973.
 *   Source SHA-256: 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 696-698.
 *   aiStep; lines 696-698.
 *   Source SHA-256: 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   updateFallFlying; lines 2590-2612.
 *   travel; lines 2019-2049.
 *   getFluidFallingAdjustedMovement; lines 2174-2186.
 *   setSprinting; lines 1895-1904.
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   tryToStartFallFlying; lines 1570-1580.
 *   Source SHA-256: 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2.
 * Bounded source decisions:
 *   Review 626fd4ffc3982169be5990949049ee0d2e3e1bb7; accepted, bounded.
 *   Reviewed snapshot df40695a6319a70dd28cf81448e39fce9f5675ff; SHA-256 60029df6f5bd84c0c0a91d67d23ea9f36d93f4af2618f9889fb1946eabaa3d2d.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java. Neither a matching archived full-file hash nor an archived matching canonical source-manifest digest was resolved for them.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class SprintFallFlyingGate implements SprintFallFlyingGateHook {
    @Override
    public boolean fallFlyingForSprintGate(Player player, ParkourVersion selected, boolean vanilla) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return vanilla;
        }

        return false;
    }
}
