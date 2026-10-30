package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.BiConsumer;

/** Resolves historical movement-list and candidate-cell traversal for inside-block callbacks. */
@MechanicType("entity.inside-blocks.scan")
public interface EntityInsideBlocksScanHook extends VersionedMechanic {
    default boolean usesHistoricalScan() {
        return true;
    }

    default <M> void scanMovements(AABB finalEntityBox, List<M> movements, BiConsumer<M, AABB> scanMovement) {
        throw new UnsupportedOperationException("Native entity-inside-block scan must remain on the native path");
    }

    default Iterable<BlockPos> candidateCells(Vec3 from, Vec3 to, AABB queryBox) {
        throw new UnsupportedOperationException("Native entity-inside-block scan must remain on the native path");
    }
}
