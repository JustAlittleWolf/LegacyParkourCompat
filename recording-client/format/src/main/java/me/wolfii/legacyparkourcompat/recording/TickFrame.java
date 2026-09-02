package me.wolfii.legacyparkourcompat.recording;

/**
 * One client tick of simulation input, facing, and observed position.
 * Positions are stored for verification; playback applies keys and facing only.
 */
public final class TickFrame {
    private final int buttons;
    private final float yaw;
    private final float pitch;
    private final double x;
    private final double y;
    private final double z;

    public TickFrame(int buttons, float yaw, float pitch, double x, double y, double z) {
        this.buttons = buttons;
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int buttons() {
        return this.buttons;
    }

    public float yaw() {
        return this.yaw;
    }

    public float pitch() {
        return this.pitch;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public boolean forward() {
        return TickButtons.isSet(this.buttons, TickButtons.FORWARD);
    }

    public boolean back() {
        return TickButtons.isSet(this.buttons, TickButtons.BACK);
    }

    public boolean left() {
        return TickButtons.isSet(this.buttons, TickButtons.LEFT);
    }

    public boolean right() {
        return TickButtons.isSet(this.buttons, TickButtons.RIGHT);
    }

    public boolean jump() {
        return TickButtons.isSet(this.buttons, TickButtons.JUMP);
    }

    public boolean sneak() {
        return TickButtons.isSet(this.buttons, TickButtons.SNEAK);
    }

    public boolean sprint() {
        return TickButtons.isSet(this.buttons, TickButtons.SPRINT);
    }

    public boolean use() {
        return TickButtons.isSet(this.buttons, TickButtons.USE);
    }
}
