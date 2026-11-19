package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Whether the movement-time fluid refresh retains the previous lava depth. */
@MechanicType("fluid.post_move_lava_depth")
public interface PostMoveLavaDepthHook extends VersionedMechanic {
    boolean retainsLavaDepth();
}
