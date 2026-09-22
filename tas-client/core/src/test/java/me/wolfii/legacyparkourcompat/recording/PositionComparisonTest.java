package me.wolfii.legacyparkourcompat.recording;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositionComparisonTest {
    @Test
    void countsAdjacentNegativeAndPositiveValues() {
        assertEquals(BigInteger.ONE, PositionComparison.ulpDistance(-1.0, Math.nextUp(-1.0)));
        assertEquals(BigInteger.ONE, PositionComparison.ulpDistance(1.0, Math.nextUp(1.0)));
        assertTrue(PositionComparison.matches(-1.0, Math.nextUp(-1.0), 0.0, 1L));
        assertFalse(PositionComparison.matches(-1.0, Math.nextUp(-1.0), 0.0, 0L));
    }

    @Test
    void definesZeroAndSubnormalBehavior() {
        assertEquals(BigInteger.ZERO, PositionComparison.ulpDistance(-0.0, +0.0));
        assertEquals(BigInteger.ONE, PositionComparison.ulpDistance(0.0, Double.MIN_VALUE));
        assertEquals(BigInteger.ONE, PositionComparison.ulpDistance(-0.0, -Double.MIN_VALUE));
        assertTrue(PositionComparison.matches(-0.0, +0.0, 0.0, 0L));
        assertFalse(PositionComparison.matches(-0.0, +0.0, 0.0, null));
    }

    @Test
    void rejectsNonfiniteValuesAndKeepsAbsoluteModeSeparate() {
        assertFalse(PositionComparison.matches(Double.NaN, Double.NaN, 0.0, 10L));
        assertFalse(PositionComparison.matches(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0, 10L));
        assertThrows(IllegalArgumentException.class, () -> PositionComparison.ulpDistance(0.0, Double.NEGATIVE_INFINITY));
        assertTrue(PositionComparison.matches(1.0, 1.1, 0.2, null));
        assertFalse(PositionComparison.matches(1.0, 1.1, 0.2, 10L));
    }
}
