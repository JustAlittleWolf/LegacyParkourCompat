package me.wolfii.legacyparkourcompat.mechanic.hook.shulker;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Supplies the historical opening-push vector for a player admitted by the opening lid. */
@MechanicType("shulker.opening_push_amount")
public interface ShulkerOpeningPushAmountHook extends VersionedMechanic {
    Vec3 pushAmount(AABB lid, Direction facing, Entity player);
}
