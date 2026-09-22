package me.wolfii.legacyparkourcompat.recording;

import java.math.BigInteger;

/** Per-coordinate comparison of finite player positions. */
public final class PositionComparison {
    private static final BigInteger UNSIGNED_LONG_MASK = BigInteger.ONE.shiftLeft(64).subtract(BigInteger.ONE);

    private PositionComparison() {
    }

    /**
     * Returns the distance in representable doubles, including subnormals and
     * negative values. Signed zero has distance zero. Nonfinite values fail.
     */
    public static BigInteger ulpDistance(double expected, double actual) {
        if (!Double.isFinite(expected) || !Double.isFinite(actual)) {
            throw new IllegalArgumentException("Player positions must be finite");
        }
        if (expected == 0.0D && actual == 0.0D) {
            return BigInteger.ZERO;
        }
        return unsigned(orderedBits(expected)).subtract(unsigned(orderedBits(actual))).abs();
    }

    public static boolean matches(double expected, double actual, double tolerance, Long maxUlps) {
        if (!Double.isFinite(expected) || !Double.isFinite(actual)) {
            return false;
        }
        if (maxUlps != null) {
            return ulpDistance(expected, actual).compareTo(BigInteger.valueOf(maxUlps)) <= 0;
        }
        return Double.compare(expected, actual) == 0
            || tolerance > 0.0D && Math.abs(expected - actual) <= tolerance;
    }

    private static long orderedBits(double value) {
        long bits = Double.doubleToRawLongBits(value);
        return bits < 0L ? ~bits : bits ^ Long.MIN_VALUE;
    }

    private static BigInteger unsigned(long value) {
        return BigInteger.valueOf(value).and(UNSIGNED_LONG_MASK);
    }
}
