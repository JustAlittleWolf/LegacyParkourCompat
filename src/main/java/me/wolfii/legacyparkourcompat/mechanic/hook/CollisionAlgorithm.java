package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * Historical entity collision / step-up ({@code Entity#collide}).
 */
@MechanicType("entity.collision")
public interface CollisionAlgorithm extends VersionedMechanic {
    Vec3 collide(Entity entity, Vec3 movement, VanillaFn<Vec3> vanilla);
}
