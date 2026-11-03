package me.wolfii.legacyparkourcompat.change.v1_15_2.shape;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.shape.CollisionQueryShapeHook;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Retains native query-shape encoding before 1.16.2. In 1.15.2 (Mojang
 * mappings), Shapes.create(AABB) uses the same floor-based findBits check as
 * 1.16.5 (Mojang mappings); 1.17.1 (Mojang mappings) adds the later rounding
 * change. Resolver selection uses this entry to fence the 1.16.2 delta.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeCollisionQueryShape implements CollisionQueryShapeHook {
    @Override
    public Optional<Encoding> encode(AABB bounds) {
        return Optional.empty();
    }
}
