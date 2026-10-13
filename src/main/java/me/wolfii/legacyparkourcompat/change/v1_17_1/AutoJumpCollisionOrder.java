package me.wolfii.legacyparkourcompat.change.v1_17_1;

import com.google.common.collect.Iterables;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 1.17.1/mojmap net/minecraft/world/level/CollisionGetter.java, getCollisions,
 * lines 57-59: block candidates precede entity candidates.
 * 1.18/mojmap same file/member, lines 67-70: entity candidates precede blocks.
 * Existing F-003 closure proves the first stable boundary at 1.18.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query) {
        return Iterables.concat(level.getBlockCollisions(player, query), level.getEntityCollisions(player, query));
    }
}
