package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Whether lava current is applied by the fluid refresh during fall-damage handling. */
@MechanicType("fluid.post_move_lava_current")
public interface PostMoveLavaCurrentHook extends VersionedMechanic {
    boolean appliesLavaCurrent(Entity entity);
}
