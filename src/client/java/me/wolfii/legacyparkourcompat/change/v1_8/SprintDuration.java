package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.WeakIdentityState;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SprintStateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTickHook;
import net.minecraft.world.entity.player.Player;

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
    private final WeakIdentityState<Player, SprintTimer> sprintTimer = new WeakIdentityState<>();

    @Override
    public void onSetSprinting(Player player, boolean sprinting) {
        this.sprintTimer.getOrCreate(player, SprintTimer::new).ticks = sprinting ? 600 : 0;
    }

    @Override
    public void tick(Player player) {
        SprintTimer timer = this.sprintTimer.get(player);
        if (timer != null && timer.ticks > 0) {
            timer.ticks--;
            if (timer.ticks == 0) {
                player.setSprinting(false);
            }
        }
    }

    private static final class SprintTimer {
        private int ticks;
    }
}
