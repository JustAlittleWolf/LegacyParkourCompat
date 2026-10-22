package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

/** Replays the final-box block callbacks at each local player movement point. */
@MechanicType("entity.block_contact_traversal")
public interface EntityBlockContactTraversalHook extends VersionedMechanic {
    /** Runs the historical final-box callbacks for one movement. */
    void afterMove(Entity entity, Consumer<BlockState> onInsideBlock);

    /** Whether the corresponding post-travel collector scan must be suppressed. */
    boolean suppressPostTravel(Entity entity);
}
