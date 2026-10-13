package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SneakInputSlowdownHook;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * The 1.14.4 pose-aware sneak-input slowdown gate.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   tick; lines [[13, 50]].
 *   Source SHA-256: 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a.
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   mobTick; lines [[701, 740]].
 *   Source SHA-256: 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf.
 * 1.14.4/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines [[13, 25]].
 *   Source SHA-256: 33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   tick; lines [[13, 26]].
 *   Source SHA-256: 5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   m_63723874; lines [[596, 598]].
 *   Source SHA-256: 708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce.
 * 1.14/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   m_63723874; lines [[597, 597]].
 *   Source SHA-256: 2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f.
 * 1.15.2/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines [[13, 26]].
 *   Source SHA-256: 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class SneakInputSlowdown implements SneakInputSlowdownHook {
    @Override
    public boolean shouldSlowDown(Player player) {
        boolean poseSlowdown = !player.getAbilities().flying
            && !player.isSwimming()
            && canFit(player, Pose.CROUCHING)
            && (player.isShiftKeyDown() || !canFit(player, Pose.STANDING));
        boolean swimmingPoseOutsideWater = !poseSlowdown
            && player.getPose() == Pose.SWIMMING
            && !player.isInWater();
        return !player.isSpectator()
            && (player.isShiftKeyDown() || poseSlowdown || swimmingPoseOutsideWater);
    }

    private static boolean canFit(Player player, Pose pose) {
        AABB box = player.getDimensions(pose).makeBoundingBox(player.position());
        return player.level().noBlockCollision(player, box) && player.level().noEntityCollision(player, box);
    }
}
