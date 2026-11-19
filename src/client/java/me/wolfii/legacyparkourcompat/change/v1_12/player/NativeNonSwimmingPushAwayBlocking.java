package me.wolfii.legacyparkourcompat.change.v1_12.player;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PushAwayBlockingHook;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/** Keeps earlier profiles on their existing push-away predicate. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeNonSwimmingPushAwayBlocking implements PushAwayBlockingHook {
    @Override
    public Optional<Boolean> isBlocking(BlockState state) {
        return Optional.empty();
    }
}
