package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintStateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintTickBehavior;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class SprintDuration implements SprintStateBehavior, SprintTickBehavior {
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
