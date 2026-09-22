package me.wolfii.legacyparkourcompat.recording;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PositionComparisonTest {
    @Test
    void countsAdjacentValuesAndSubnormals() {
        assertEquals(BigInteger.ONE, PositionComparison.ulpDistance(1.0D, Math.nextUp(1.0D)));
        assertEquals(BigInteger.ONE, PositionComparison.ulpDistance(0.0D, Double.MIN_VALUE));
        assertEquals(BigInteger.valueOf(2L), PositionComparison.ulpDistance(-Double.MIN_VALUE, Double.MIN_VALUE));
        assertEquals(BigInteger.ZERO, PositionComparison.ulpDistance(-0.0D, 0.0D));
    }

    @Test
    void ulpModeUsesInclusiveLimitAndPreservesExactDefault() {
        double shifted = Math.nextUp(1.0D);
        assertTrue(PositionComparison.matches(1.0D, shifted, 0.0D, 1L));
        assertFalse(PositionComparison.matches(1.0D, shifted, 0.0D, 0L));
        assertFalse(PositionComparison.matches(1.0D, shifted, 0.0D, null));
        assertFalse(PositionComparison.matches(-0.0D, 0.0D, 0.0D, null));
        assertTrue(PositionComparison.matches(-0.0D, 0.0D, 0.0D, 0L));
    }

    @Test
    void rejectsNonFiniteValuesAndHandlesHugeCrossZeroDistance() {
        assertFalse(PositionComparison.matches(Double.NaN, Double.NaN, 0.0D, 10L));
        assertFalse(PositionComparison.matches(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0D, 10L));
        assertThrows(IllegalArgumentException.class, () -> PositionComparison.ulpDistance(Double.NaN, 0.0D));
        assertTrue(PositionComparison.ulpDistance(-Double.MAX_VALUE, Double.MAX_VALUE)
            .compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0);
    }
}
