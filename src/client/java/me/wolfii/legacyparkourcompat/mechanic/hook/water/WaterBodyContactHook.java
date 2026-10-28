package me.wolfii.legacyparkourcompat.mechanic.hook.water;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;
import java.util.Optional;

@MechanicType("water.body_contact")
public interface WaterBodyContactHook extends VersionedMechanic {
    /** Returns the query AABB for body-contact water presence, if this profile replaces it. */
    Optional<AABB> probe(AABB body);

    /** Applies the liquid-surface test using the contact scan's exclusive maximum Y. */
    boolean includes(int exclusiveMaxY, int cellY, int liquidLevel);
}
