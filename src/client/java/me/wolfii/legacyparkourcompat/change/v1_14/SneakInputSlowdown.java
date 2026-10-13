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
 *   tick.
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   mobTick.
 * 1.14.4/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   tick.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   m_63723874.
 * 1.14/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   m_63723874.
 * 1.15.2/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR remains unavailable; revised source equality does not prove original derived-JAR equivalence.
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
