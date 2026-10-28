package me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback;

import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/** Per-entity storage for the source's nested callback movement batches. */
public interface MovementCallbackSegmentsAccess {
    void legacyparkourcompat$addMovementCallbackBatch(MoverType moverType, List<Segment> segments);

    List<Batch> legacyparkourcompat$takeMovementCallbackBatches();

    boolean legacyparkourcompat$removeLatestPistonCallbackBatch();

    boolean legacyparkourcompat$isExplicitCallbackScan();

    record Segment(Vec3 from, Vec3 to) {}

    record Batch(MoverType moverType, List<Segment> segments) {}
}
