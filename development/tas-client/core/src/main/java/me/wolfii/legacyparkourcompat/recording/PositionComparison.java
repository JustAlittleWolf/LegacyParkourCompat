package me.wolfii.legacyparkourcompat.recording;

import java.math.BigInteger;

/** Per-coordinate position comparison shared by live playback and reference tooling. */
public final class PositionComparison {
    private PositionComparison() {
    }

    /**
     * Finite values count adjacent representable numbers on either side of zero;
     * both signed zeros have distance zero. Adjacent subnormals have distance one.
     * NaN and infinities have no ULP distance.
     */
    public static BigInteger ulpDistance(double left, double right) {
        if (!Double.isFinite(left) || !Double.isFinite(right)) {
            throw new IllegalArgumentException("ULP distance requires finite coordinates");
        }
        long a = Double.doubleToRawLongBits(left);
        long b = Double.doubleToRawLongBits(right);
        BigInteger aMagnitude = BigInteger.valueOf(a & Long.MAX_VALUE);
        BigInteger bMagnitude = BigInteger.valueOf(b & Long.MAX_VALUE);
        return (a < 0) == (b < 0)
            ? aMagnitude.subtract(bMagnitude).abs()
            : aMagnitude.add(bMagnitude);
    }

    public static boolean matches(double expected, double actual, double tolerance, Long maxUlps) {
        if (!Double.isFinite(expected) || !Double.isFinite(actual)) {
            return false;
        }
        if (maxUlps != null) {
            return ulpDistance(expected, actual).compareTo(BigInteger.valueOf(maxUlps.longValue())) <= 0;
        }
        return Double.compare(expected, actual) == 0
            || (tolerance > 0.0D && Math.abs(expected - actual) <= tolerance);
    }
}
