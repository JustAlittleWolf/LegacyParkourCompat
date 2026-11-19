package me.wolfii.legacyparkourcompat.mechanic.hook.shulker;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.AABB;

/** Defines the shulker box opening push query for the local player. */
@MechanicType("shulker.opening_push_query")
public interface ShulkerOpeningPushQueryHook extends VersionedMechanic {
    AABB openingPushBounds(ShulkerBoxBlockEntity box);
}
