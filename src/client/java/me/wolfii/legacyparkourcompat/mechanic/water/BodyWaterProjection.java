package me.wolfii.legacyparkourcompat.mechanic.water;

/** Body observations from an interaction refresh, independent of its eye and current tracker. */
public interface BodyWaterProjection {
    boolean legacyparkourcompat$bodyWaterContact(boolean nativeContact);
    double legacyparkourcompat$bodyWaterDepth(double nativeDepth);
}
