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
 *   public void mobTick().
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-TICK-01-sprint-timeout is unknown within (1.8.9, 1.9.4]
 *   Exact independent source verdict/snapshot identity for MD-01-sprint-timeout
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
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
