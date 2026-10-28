package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpBorderCandidateHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.15.2 Mojmap net.minecraft.world.level.CollisionGetter#getBlockCollisions first tests the player's
 * box deflated by 1.0E-7 against the border shape, then tests the inflated box;
 * it yields the border candidate only when the first overlap is false and the
 * second is true. Its net.minecraft.client.player.LocalPlayer#updateAutoJump flattens these candidates and
 * scans them in order. The 1.16 Mojmap net.minecraft.world.level.CollisionSpliterator#worldBorderCheck
 * adds CollisionSpliterator#isBoxFullyWithinWorldBorder's rounded-bound fast rejection before the
 * same overlap tests. The V1_15 provider preserves the native candidate stream
 * before the inspected 1.15.2 boundary, whose earlier applicability is unproved.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class AutoJumpBorderCandidate implements AutoJumpBorderCandidateHook {
    @Override
    public Optional<VoxelShape> borderCandidate(Player player, ParkourVersion selected) {
        WorldBorder border = player.level().getWorldBorder();
        AABB box = player.getBoundingBox();
        VoxelShape shape = border.getCollisionShape();
        boolean overlapsDeflated = Shapes.joinIsNotEmpty(
            shape,
            Shapes.create(box.deflate(1.0E-7)),
            BooleanOp.AND
        );
        boolean overlapsInflated = Shapes.joinIsNotEmpty(
            shape,
            Shapes.create(box.inflate(1.0E-7)),
            BooleanOp.AND
        );
        return !overlapsDeflated && overlapsInflated ? Optional.of(shape) : Optional.empty();
    }
}
