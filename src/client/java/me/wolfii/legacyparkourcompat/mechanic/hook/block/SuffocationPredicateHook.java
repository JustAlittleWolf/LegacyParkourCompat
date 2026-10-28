package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/** Supplies an optional historical override for a block state's suffocation predicate. */
@MechanicType("block.suffocating")
public interface SuffocationPredicateHook extends VersionedMechanic {
    Optional<Boolean> suffocating(BlockState state);
}
