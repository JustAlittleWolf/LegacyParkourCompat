package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerFallDistanceResetHook;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * The move bodies in 1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.2, 1.13.2 and
 * 1.14.4/ornithe-feather/net/minecraft/entity/Entity.java, and 1.15.2, 1.16.5,
 * 1.17.1, 1.18 and 1.18.1/mojmap/net/minecraft/world/entity/Entity.java,
 * have no direct fall-distance reset. 1.18.2 Entity.move adds the downward
 * movement clip/reset before writing position. The resolver-selected old
 * operation suppresses only that new call; fall, fluid and block callbacks
 * retain their independent fall-distance writers.
 * 1.17.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 * 1.18.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class FallDistanceReset implements PlayerFallDistanceResetHook {
    @Override
    public boolean suppressMovementFallDistanceReset(ParkourVersion selected) {
        return true;
    }
}
