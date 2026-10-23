package me.wolfii.legacyparkourcompat.mechanic.hook.water;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;
import java.util.Optional;

@MechanicType("water.body_contact")
public interface WaterBodyContactHook extends VersionedMechanic {
    Optional<AABB> probe(AABB body);
    boolean includes(int exclusiveMaxY, int cellY, int liquidLevel);
}
