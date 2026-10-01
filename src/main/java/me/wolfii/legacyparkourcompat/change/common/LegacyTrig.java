package me.wolfii.legacyparkourcompat.change.common;
/** Float lookup indexing used by the cited historical MathHelper/Mth sources. */
public final class LegacyTrig {
    private static final float[] SIN = new float[65536];
    static {
        for (int i = 0; i < SIN.length; i++) SIN[i] = (float)Math.sin(i * Math.PI * 2.0 / 65536.0);
    }
    private LegacyTrig() {}
    public static float sin(float value) { return SIN[(int)(value * 10430.378F) & 65535]; }
    public static float cos(float value) { return SIN[(int)(value * 10430.378F + 16384.0F) & 65535]; }
}
