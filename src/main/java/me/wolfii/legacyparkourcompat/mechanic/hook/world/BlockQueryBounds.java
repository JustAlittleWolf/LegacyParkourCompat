package me.wolfii.legacyparkourcompat.mechanic.hook.world;

/** Inclusive block-coordinate bounds for a world block-collision query. */
public record BlockQueryBounds(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
}
