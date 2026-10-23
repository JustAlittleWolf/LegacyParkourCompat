package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/** Selects the displacement used to order axis-separated block callbacks. */
@MechanicType("block.callback_axis_order")
public interface BlockCallbackAxisOrderHook extends VersionedMechanic {
    Vec3 callbackAxisOrder(Vec3 recordedMovement, Vec3 resolvedMovement);
}
