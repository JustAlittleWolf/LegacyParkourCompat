package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Ornithe Feather 1.13.2 PlayerEntity#updatePlayerPose uses a 0.6 by 1.65
 * crouch box; Mojmap 1.14.4 Player#updatePlayerPose selects the 0.6 by 1.5
 * crouch dimensions. Unobfuscated 26.2 Avatar#getDefaultDimensions supplies
 * the native dimensions, including modern width and attachments, which this
 * hook preserves while restoring the historical height. Modern swimming and
 * crawl dimensions stay native.
 * The independently resolved PlayerPoseHook adapts the historical block-only resize
 * after native selection while preserving modern crawl admission.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   updatePlayerPose.
 * 1.14.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   updatePlayerPose.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SneakingDimensions implements PlayerDimensionsHook {
    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.CROUCHING) {
            return new EntityDimensions(
                vanilla.width(), 1.65F, vanilla.eyeHeight(), vanilla.attachments(), vanilla.fixed()
            );
        }
        return vanilla;
    }
}
