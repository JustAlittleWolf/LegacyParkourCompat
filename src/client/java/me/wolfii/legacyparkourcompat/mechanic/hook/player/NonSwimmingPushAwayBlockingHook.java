package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * The non-swimming local-player push-away predicate for one registered block.
 * This hook is only for the direct player response; ordinary suffocation and
 * other block-state queries keep their native predicate.
 */
@MechanicType("player.push-away.non-swimming-blocking")
public interface NonSwimmingPushAwayBlockingHook extends VersionedMechanic {
    /** An empty result leaves the state outside this predicate override. */
    Optional<Boolean> isBlocking(BlockState state);
}
