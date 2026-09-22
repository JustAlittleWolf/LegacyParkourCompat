package me.wolfii.legacyparkourcompat.change;

/** Minecraft's float-indexed sine table through 1.20.4. */
public final class HistoricalTrig {
    private static final float[] SINE_TABLE = new float[65536];

    static {
        for (int index = 0; index < SINE_TABLE.length; index++) {
            SINE_TABLE[index] = (float) Math.sin(index * Math.PI * 2.0 / 65536.0);
        }
    }

    private HistoricalTrig() {
    }

    public static float sin(float angle) {
        return SINE_TABLE[(int) (angle * 10430.378F) & 65535];
    }

    public static float cos(float angle) {
        return SINE_TABLE[(int) (angle * 10430.378F + 16384.0F) & 65535];
    }
}
