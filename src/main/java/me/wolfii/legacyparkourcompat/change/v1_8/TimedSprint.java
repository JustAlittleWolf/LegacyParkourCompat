package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintDurationBehavior;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

/** 1.8 automatically ended a sprint after 600 movement ticks. */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class TimedSprint implements SprintDurationBehavior {
    private final Map<Player, Integer> remainingTicks = new WeakHashMap<>();
    private int epoch = Integer.MIN_VALUE;

    private void refreshEpoch() {
        int currentEpoch = MovementRuntime.epoch();
        if (this.epoch != currentEpoch) {
            this.remainingTicks.clear();
            this.epoch = currentEpoch;
        }
    }

    @Override
    public synchronized void onSetSprinting(Player player, boolean sprinting) {
        refreshEpoch();
        if (sprinting) {
            this.remainingTicks.put(player, 600);
        } else {
            this.remainingTicks.remove(player);
        }
    }

    @Override
    public synchronized void tick(Player player) {
        refreshEpoch();
        Integer remaining = this.remainingTicks.get(player);
        if (remaining == null && player.isSprinting()) {
            remaining = 600;
        }
        if (remaining == null) {
            return;
        }
        if (remaining <= 1) {
            this.remainingTicks.remove(player);
            player.setSprinting(false);
        } else {
            this.remainingTicks.put(player, remaining - 1);
        }
    }
}
