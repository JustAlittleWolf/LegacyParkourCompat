package me.wolfii.legacyparkourcompat.mechanic.hook.shulker;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Supplies historical candidate bounds for the local player's Shulker peek push. */
@MechanicType("shulker.peek_push_query")
public interface ShulkerPeekPushQueryHook extends VersionedMechanic {
    AABB bounds(float scale, Direction direction, float progressFrom, float progressTo, Vec3 position);
}
