package me.wolfii.legacyparkourcompat.change.v1_17_1;

import com.google.common.collect.Iterables;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpCollisionOrderBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderBehavior {
    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query) {
        return Iterables.concat(level.getBlockCollisions(player, query), level.getEntityCollisions(player, query));
    }
}
