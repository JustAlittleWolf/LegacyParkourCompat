package me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Enumerates one callback segment; empty preserves the native iterator. */
@MechanicType("block_callback.segment_iterator")
public interface BlockCallbackTraversalHook extends VersionedMechanic {
    default boolean appliesTo(Entity entity) {
        return true;
    }

    Optional<Boolean> visitSegment(Vec3 from, Vec3 to, AABB target, BlockGetter.BlockStepVisitor visitor);
}
