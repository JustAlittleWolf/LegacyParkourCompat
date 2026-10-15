package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpBorderCandidateHook;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Mojmap 1.16, 1.16.5, 1.17 and 1.17.1 CollisionSpliterator.tryAdvance /
 * worldBorderCheck emit the border before blocks, sampling the current player
 * box rather than the auto-jump query. Preserve strict rounded-bound tests and
 * short-circuited deflate/inflate overlap checks. A border AABB's infinite top
 * can mask a step in LocalPlayer.updateAutoJump and prevent autoJumpTime = 1.
 * Mojmap 1.18 CollisionGetter.getCollisions removes that border candidate;
 * borderCollision is consulted separately by noCollision. Earlier spliterator
 * applicability is unresolved, so profiles before 1.16 keep their existing scan.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class AutoJumpBorderCandidate implements AutoJumpBorderCandidateHook {
    @Override
    public Optional<VoxelShape> borderCandidate(Player player, ParkourVersion selected) {
        if (selected.olderThan(ParkourVersion.V1_16)) {
            return Optional.empty();
        }
        WorldBorder border = player.level().getWorldBorder();
        AABB box = player.getBoundingBox();
        double minX = Mth.floor(border.getMinX());
        double minZ = Mth.floor(border.getMinZ());
        double maxX = Mth.ceil(border.getMaxX());
        double maxZ = Mth.ceil(border.getMaxZ());
        if (box.minX > minX && box.minX < maxX && box.minZ > minZ && box.minZ < maxZ
            && box.maxX > minX && box.maxX < maxX && box.maxZ > minZ && box.maxZ < maxZ) {
            return Optional.empty();
        }
        VoxelShape shape = border.getCollisionShape();
        return !Shapes.joinIsNotEmpty(shape, Shapes.create(box.deflate(1.0E-7)), BooleanOp.AND)
            && Shapes.joinIsNotEmpty(shape, Shapes.create(box.inflate(1.0E-7)), BooleanOp.AND)
            ? Optional.of(shape) : Optional.empty();
    }
}
