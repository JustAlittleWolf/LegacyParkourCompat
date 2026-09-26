package me.wolfii.legacyparkourcompat.recording;

import java.util.ArrayList;
import java.util.List;

/** Pure recording edits; replay input and facing stay byte-for-byte equivalent. */
public final class ReusableRecordingPlacement {
    private ReusableRecordingPlacement() { }

    public static MovementRecording trimStationaryEnds(MovementRecording source) {
        List<TickFrame> ticks = source.ticks();
        int first = 0;
        double previousX = source.startX(), previousY = source.startY(), previousZ = source.startZ();
        while (first < ticks.size()) {
            TickFrame frame = ticks.get(first);
            if (!same(frame, previousX, previousY, previousZ)) break;
            previousX = frame.x(); previousY = frame.y(); previousZ = frame.z();
            first++;
        }
        int end = ticks.size();
        while (end > first) {
            TickFrame frame = ticks.get(end - 1);
            TickFrame preceding = end - 2 >= first ? ticks.get(end - 2) : null;
            if (preceding == null || !same(frame, preceding.x(), preceding.y(), preceding.z())) break;
            end--;
        }
        if (first == end) return source;
        return new MovementRecording(source.startX(), source.startY(), source.startZ(), source.startYaw(), source.startPitch(),
            source.startVelocityX(), source.startVelocityY(), source.startVelocityZ(), ticks.subList(first, end), source.setup());
    }

    public static MovementRecording moveStartTo(MovementRecording source, double x, double y, double z) {
        if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("Reusable start position must be finite");
        }
        double dx = x - source.startX(), dy = y - source.startY(), dz = z - source.startZ();
        List<TickFrame> shifted = new ArrayList<TickFrame>(source.ticks().size());
        for (TickFrame tick : source.ticks()) {
            shifted.add(new TickFrame(tick.buttons(), tick.yaw(), tick.pitch(),
                tick.x() + dx, tick.y() + dy, tick.z() + dz));
        }
        return new MovementRecording(x, y, z, source.startYaw(), source.startPitch(),
            source.startVelocityX(), source.startVelocityY(), source.startVelocityZ(), shifted, source.setup());
    }

    private static boolean same(TickFrame frame, double x, double y, double z) {
        return Double.compare(frame.x(), x) == 0 && Double.compare(frame.y(), y) == 0
            && Double.compare(frame.z(), z) == 0;
    }
}
