package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PushAwayVelocityHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source-matched float response shared by the accepted 1.8.9 and 1.9.4 endpoints.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   pushAwayFrom fixed float response; lines [[292, 338]].
 *   Source SHA-256: 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   pushAwayFrom fixed float response; lines [[349, 395]].
 *   Source SHA-256: 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d.
 * Candidate locators only: operation fidelity, independent review and profile applicability remain open.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class PushAwayVelocity implements PushAwayVelocityHook {
    @Override
    public double pushAwayVelocity(Player player, ParkourVersion target, double vanilla) {
        if (target != ParkourVersion.V1_8 && target != ParkourVersion.V1_9) {
            return vanilla;
        }

        // The float is widened when assigned to the double velocity fields.
        float g = 0.1F;
        return vanilla < 0.0D ? -g : g;
    }
}
