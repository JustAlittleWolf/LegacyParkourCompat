package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SprintStateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTickHook;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 488-637.
 *   Source SHA-256: 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 604-760.
 *   Source SHA-256: 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d.
 * Bounded source decisions:
 *   Review 304b6a58384a9a724d3ab9ad3f3bab5650bacdf5; accepted, bounded.
 *   Reviewed snapshot 1dbafbb7899c57ddf1718e421aa2c32e283fc7c7; SHA-256 f7fb9e8aaf8685c65a2af1ce23c43f9d52e44393280c4047a5fd4d6cfc0788d5.
 *   Review 304b6a58384a9a724d3ab9ad3f3bab5650bacdf5; accepted, bounded.
 *   Reviewed snapshot 1dbafbb7899c57ddf1718e421aa2c32e283fc7c7; SHA-256 f7fb9e8aaf8685c65a2af1ce23c43f9d52e44393280c4047a5fd4d6cfc0788d5.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-TICK-01-sprint-timeout is unknown within (1.8.9, 1.9.4]
 *   Exact independent source verdict/snapshot identity for MD-01-sprint-timeout
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class SprintDuration implements SprintStateHook, SprintTickHook {
    private final Map<Player, Integer> sprintTimers = new WeakHashMap<>();

    @Override
    public void onSetSprinting(Player player, boolean sprinting) {
        this.sprintTimers.put(player, sprinting ? 600 : 0);
    }

    @Override
    public void tick(Player player) {
        int timer = this.sprintTimers.getOrDefault(player, 0);
        if (timer > 0) {
            timer--;
            this.sprintTimers.put(player, timer);
            if (timer == 0) {
                player.setSprinting(false);
            }
        }
    }
}
