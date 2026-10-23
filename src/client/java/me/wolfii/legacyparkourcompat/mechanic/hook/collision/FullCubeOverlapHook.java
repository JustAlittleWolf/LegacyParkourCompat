package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;

/** Selects the overlap predicate used for full-cube block collision candidates. */
@MechanicType("collision.full-cube-overlap")
public interface FullCubeOverlapHook extends VersionedMechanic {
    boolean overlaps(AABB query, double x, double y, double z, double maxX, double maxY, double maxZ,
                     VanillaFn<Boolean> vanilla);
}
