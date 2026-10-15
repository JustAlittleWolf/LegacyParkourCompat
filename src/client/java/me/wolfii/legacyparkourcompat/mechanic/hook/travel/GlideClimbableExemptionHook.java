package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;

/** Gliding exception before the native climbable predicate and cache writer. */
@MechanicType("player.climbable.glide_exemption")
public interface GlideClimbableExemptionHook extends VersionedMechanic {
    boolean exemptsGlidingPlayer(ParkourVersion selected, BlockState state, boolean gliding);
}
