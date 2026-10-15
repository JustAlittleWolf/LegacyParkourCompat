package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Pose;

/** Selects the candidate-box inset for historical player pose-clearance queries. */
@MechanicType("player.pose-clearance")
public interface PoseClearanceHook extends VersionedMechanic {
    boolean usesFullBox(ParkourVersion selected, Pose pose);
}
