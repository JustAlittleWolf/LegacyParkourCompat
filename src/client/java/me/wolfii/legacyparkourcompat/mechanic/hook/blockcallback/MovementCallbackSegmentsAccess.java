package me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/** Per-entity storage for the source's nested callback movement batches. */
public interface MovementCallbackSegmentsAccess {
    void legacyparkourcompat$addMovementCallbackBatch(List<Segment> segments);

    List<Batch> legacyparkourcompat$takeMovementCallbackBatches();

    boolean legacyparkourcompat$removeLatestMovementCallbackBatch();

    record Segment(Vec3 from, Vec3 to) {}

    record Batch(List<Segment> segments) {}

    /** The no-argument scan owns this exact native list. Explicit scans supply their own list. */
    record Snapshot(List<?> nativeMovements, List<Segment> segments) {
        public static Snapshot prepare(List<?> nativeMovements, List<Batch> batches, Vec3 oldPosition, Vec3 position) {
            List<Segment> segments = new ArrayList<>();
            for (Batch batch : batches) {
                segments.addAll(batch.segments());
            }
            if (segments.isEmpty()) {
                segments.add(new Segment(oldPosition, position));
            } else {
                Vec3 lastEndpoint = segments.getLast().to();
                if (lastEndpoint.distanceToSqr(position) > 9.9999994E-11F) {
                    segments.add(new Segment(lastEndpoint, position));
                }
            }
            return new Snapshot(nativeMovements, segments);
        }
    }
}
