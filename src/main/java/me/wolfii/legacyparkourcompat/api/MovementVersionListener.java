package me.wolfii.legacyparkourcompat.api;

@FunctionalInterface
public interface MovementVersionListener {
    void onMovementVersionChanged(ParkourVersion previous, ParkourVersion current);
}
