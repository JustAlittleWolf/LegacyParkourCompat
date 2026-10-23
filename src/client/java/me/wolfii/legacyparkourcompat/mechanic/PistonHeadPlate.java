package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

/** Head plate geometry identical in 1.8.9 and 1.9.4 ornithe-feather PistonHeadBlock. */
public final class PistonHeadPlate {
    private PistonHeadPlate() {}

    public static AABB head(Direction facing) {
        return switch (facing) {
            case DOWN -> new AABB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
            case UP -> new AABB(0.0, 0.75, 0.0, 1.0, 1.0, 1.0);
            case NORTH -> new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 0.25);
            case SOUTH -> new AABB(0.0, 0.0, 0.75, 1.0, 1.0, 1.0);
            case WEST -> new AABB(0.0, 0.0, 0.0, 0.25, 1.0, 1.0);
            case EAST -> new AABB(0.75, 0.0, 0.0, 1.0, 1.0, 1.0);
        };
    }
}
