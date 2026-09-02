package me.wolfii.legacyparkourcompat.recording;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Version-stable recording of simulation keys, facing, and positions.
 */
public final class MovementRecording {
    public static final int FORMAT_VERSION = 1;

    private final double startX;
    private final double startY;
    private final double startZ;
    private final float startYaw;
    private final float startPitch;
    private final List<TickFrame> ticks;

    public MovementRecording(
        double startX,
        double startY,
        double startZ,
        float startYaw,
        float startPitch,
        List<TickFrame> ticks
    ) {
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.startYaw = startYaw;
        this.startPitch = startPitch;
        this.ticks = Collections.unmodifiableList(new ArrayList<TickFrame>(ticks));
    }

    public double startX() {
        return this.startX;
    }

    public double startY() {
        return this.startY;
    }

    public double startZ() {
        return this.startZ;
    }

    public float startYaw() {
        return this.startYaw;
    }

    public float startPitch() {
        return this.startPitch;
    }

    public List<TickFrame> ticks() {
        return this.ticks;
    }
}
