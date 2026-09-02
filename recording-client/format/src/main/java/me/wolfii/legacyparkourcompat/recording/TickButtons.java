package me.wolfii.legacyparkourcompat.recording;

/**
 * Simulation inputs captured for one client tick. Bits are stable across
 * Minecraft versions so recordings can be replayed on another client.
 */
public final class TickButtons {
    public static final int FORWARD = 1;
    public static final int BACK = 1 << 1;
    public static final int LEFT = 1 << 2;
    public static final int RIGHT = 1 << 3;
    public static final int JUMP = 1 << 4;
    public static final int SNEAK = 1 << 5;
    public static final int SPRINT = 1 << 6;
    public static final int USE = 1 << 7;

    private TickButtons() {
    }

    public static int pack(
        boolean forward,
        boolean back,
        boolean left,
        boolean right,
        boolean jump,
        boolean sneak,
        boolean sprint,
        boolean use
    ) {
        int bits = 0;
        if (forward) {
            bits |= FORWARD;
        }
        if (back) {
            bits |= BACK;
        }
        if (left) {
            bits |= LEFT;
        }
        if (right) {
            bits |= RIGHT;
        }
        if (jump) {
            bits |= JUMP;
        }
        if (sneak) {
            bits |= SNEAK;
        }
        if (sprint) {
            bits |= SPRINT;
        }
        if (use) {
            bits |= USE;
        }
        return bits;
    }

    public static boolean isSet(int bits, int flag) {
        return (bits & flag) != 0;
    }
}
