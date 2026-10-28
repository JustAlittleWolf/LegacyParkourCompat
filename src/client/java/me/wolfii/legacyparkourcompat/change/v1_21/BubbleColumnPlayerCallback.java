package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.BubbleColumnPlayerCallbackHook;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * Mojmap 1.21.1 Player inherits Entity.onAboveBubbleCol and
 * Entity.onInsideBubbleColumn without a flight guard; the audited 1.21 Player.java
 * is byte-identical. In 1.21.2, Player overrides both callbacks and delegates to
 * Entity only when Abilities.flying is false. Force the inherited 1.21 response
 * for either callback throughout the audited 1.21/1.21.1 interval.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class BubbleColumnPlayerCallback implements BubbleColumnPlayerCallbackHook {
    public BubbleColumnPlayerCallback() {
    }

    @Override
    public Optional<Boolean> forceLegacyCallback(Player player) {
        return Optional.of(true);
    }
}
