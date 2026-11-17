package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Supplies the historical world-boundary result for a player's pose collision query. */
@MechanicType("player.pose-collision-world-bounds")
public interface PoseCollisionWorldBoundsHook extends VersionedMechanic {
    /**
     * Returns whether the historical block-collision scan would reject this candidate box at the world boundary.
     * The caller combines this with the native block-only collision result.
     */
    boolean hasOutOfBoundsCollision(
        double minX, double minY, double minZ,
        double maxX, double maxY, double maxZ
    );
}
