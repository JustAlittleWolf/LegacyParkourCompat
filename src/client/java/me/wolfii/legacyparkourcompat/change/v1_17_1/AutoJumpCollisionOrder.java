package me.wolfii.legacyparkourcompat.change.v1_17_1;

import com.google.common.collect.Iterables;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 1.17.1/mojmap net/minecraft/world/level/CollisionGetter.java, getCollisions,
 *: block candidates precede entity candidates.
 * 1.18/mojmap same file/member: entity candidates precede blocks.
 * Existing F-003 closure proves the first stable boundary at 1.18.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump.
 * 1.17.1/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   getCollisions.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump.
 * 1.18.2/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   getCollisions.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
                                                  ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        // Auto-jump is a modern feature for profiles before its introduction.
        if (selected.olderThan(ParkourVersion.V1_10)) {
            return vanilla.get();
        }
        return Iterables.concat(level.getBlockCollisions(player, query), level.getEntityCollisions(player, query));
    }
}
