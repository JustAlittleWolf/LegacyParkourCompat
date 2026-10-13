package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Supplies collision candidates in the historical order used by auto-jump. */
@FunctionalInterface
@MechanicType("player.auto_jump.collision_order")
public interface AutoJumpCollisionOrderHook extends VersionedMechanic {
    Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
                                           ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla);
}
