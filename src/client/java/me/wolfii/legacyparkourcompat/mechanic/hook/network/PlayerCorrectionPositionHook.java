package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/** Current position after resolving a local player's correction packet coordinates. */
@MechanicType("player.correction.position")
public interface PlayerCorrectionPositionHook extends VersionedMechanic {
    Vec3 correctionPosition(Vec3 position, ParkourVersion selected);
}
