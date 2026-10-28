package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.BubbleColumnPlayerCallbackHook;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * Mojmap 1.21.2 and 1.21.3 Player.onAboveBubbleCol and
 * Player.onInsideBubbleColumn delegate to Entity only when Abilities.flying is
 * false. This resolver stop point leaves those native callbacks unchanged; it
 * makes no source claim for releases after the audited 1.21.2/1.21.3 pair.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class BubbleColumnPlayerCallback implements BubbleColumnPlayerCallbackHook {
    public BubbleColumnPlayerCallback() {
    }

    @Override
    public Optional<Boolean> forceLegacyCallback(Player player) {
        return Optional.empty();
    }
}
