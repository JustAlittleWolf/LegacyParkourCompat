package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedUpdateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep; lines [[504, 510]].
 *   Source SHA-256: 724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 642-716.
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getFrictionInfluencedSpeed; lines 2206-2207.
 *   aiStep; lines 2461-2584.
 *   getFrictionInfluencedSpeed; lines 2206-2207.
 *   Source SHA-256: db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep; lines [[506, 512]].
 *   aiStep; lines 508-513.
 *   Source SHA-256: bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 652-819.
 *   Source SHA-256: 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479.
 * 1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement; lines 2201-2248.
 *   Source SHA-256: 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db.
 * 1.19.3/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep; lines 496-517.
 *   Source SHA-256: 02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 652-819.
 *   Source SHA-256: 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getFrictionInfluencedSpeed; lines 2197-2198.
 *   handleRelativeFrictionAndCalculateMovement; lines 2152-2203.
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   getFlyingSpeed; lines 2112-2117.
 *   aiStep; lines 499-510.
 *   getFlyingSpeed; lines 2112-2118.
 *   Source SHA-256: 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-012-sprint-air-control-float.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-F012-independent-boundary-review.md; ACCEPT, sampled source boundary.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class AirSpeed implements AirSpeedHook, AirSpeedUpdateHook {
    @Override
    public float speed(Player player, float stored, float vanilla) {
        return player.getAbilities().flying && !player.isPassenger() ? vanilla : stored;
    }

    @Override
    public float afterAiStep(Player player) {
        float speed = 0.02F;
        if (player.isSprinting()) {
            speed += 0.006F;
        }
        return speed;
    }
}
