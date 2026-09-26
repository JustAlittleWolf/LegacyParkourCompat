package me.wolfii.legacyparkourcompat.recording;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.JsonObject;

/**
 * Version-stable recording of simulation keys, facing, and positions.
 */
public final class MovementRecording {
    public static final int FORMAT_VERSION = 2;

    private final double startX;
    private final double startY;
    private final double startZ;
    private final float startYaw;
    private final float startPitch;
    private final double startVelocityX;
    private final double startVelocityY;
    private final double startVelocityZ;
    private final List<TickFrame> ticks;
    private final JsonObject setup;

    public MovementRecording(
        double startX,
        double startY,
        double startZ,
        float startYaw,
        float startPitch,
        List<TickFrame> ticks
    ) {
        this(startX, startY, startZ, startYaw, startPitch, 0.0, 0.0, 0.0, ticks);
    }

    public MovementRecording(
        double startX, double startY, double startZ, float startYaw, float startPitch,
        double startVelocityX, double startVelocityY, double startVelocityZ, List<TickFrame> ticks
    ) {
        this(startX, startY, startZ, startYaw, startPitch, startVelocityX, startVelocityY, startVelocityZ, ticks, null);
    }

    public MovementRecording(
        double startX, double startY, double startZ, float startYaw, float startPitch,
        double startVelocityX, double startVelocityY, double startVelocityZ, List<TickFrame> ticks, JsonObject setup
    ) {
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.startYaw = startYaw;
        this.startPitch = startPitch;
        this.startVelocityX = startVelocityX;
        this.startVelocityY = startVelocityY;
        this.startVelocityZ = startVelocityZ;
        this.ticks = Collections.unmodifiableList(new ArrayList<TickFrame>(ticks));
        this.setup = RecordingSetup.normalize(setup);
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

    public double startVelocityX() { return this.startVelocityX; }
    public double startVelocityY() { return this.startVelocityY; }
    public double startVelocityZ() { return this.startVelocityZ; }

    public List<TickFrame> ticks() {
        return this.ticks;
    }

    public JsonObject setup() { return this.setup.deepCopy(); }

    public MovementRecording withSetup(JsonObject replacement) {
        return new MovementRecording(startX, startY, startZ, startYaw, startPitch,
            startVelocityX, startVelocityY, startVelocityZ, ticks, replacement);
    }
}
