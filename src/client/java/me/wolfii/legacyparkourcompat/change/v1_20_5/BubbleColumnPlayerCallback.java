package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.BubbleColumnPlayerCallbackHook;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/** Resolver stop point before the audited interval; this is not a source claim. */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class BubbleColumnPlayerCallback implements BubbleColumnPlayerCallbackHook {
    public BubbleColumnPlayerCallback() {
    }

    @Override
    public Optional<Boolean> forceLegacyCallback(Player player) {
        return Optional.empty();
    }
}
