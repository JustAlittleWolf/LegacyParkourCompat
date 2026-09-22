package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientUnstuckBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Through 1.16.1, any suffocating block in the player's Y column pushes out. */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class WholeColumnUnstuck implements ClientUnstuckBehavior {
    @Override
    public void moveTowardsClosestSpace(Player player, double x, double z, VanillaCall vanilla) {
        if (MovementRuntime.profile(player).target().olderThan(ParkourVersion.V1_14)) {
            vanilla.run();
            return;
        }
        BlockPos pos = BlockPos.containing(x, player.getY(), z);
        if (!blocked(player, pos)) {
            return;
        }

        double offsetX = x - pos.getX();
        double offsetZ = z - pos.getZ();
        Direction closest = null;
        double best = 9999.0;
        for (Direction direction : new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}) {
            double distance = direction.getAxis() == Direction.Axis.X ? offsetX : offsetZ;
            if (direction.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
                distance = 1.0 - distance;
            }
            if (!blocked(player, pos.relative(direction)) && distance < best) {
                best = distance;
                closest = direction;
            }
        }
        if (closest != null) {
            Vec3 velocity = player.getDeltaMovement();
            if (closest.getAxis() == Direction.Axis.X) {
                player.setDeltaMovement(0.1 * closest.getStepX(), velocity.y, velocity.z);
            } else {
                player.setDeltaMovement(velocity.x, velocity.y, 0.1 * closest.getStepZ());
            }
        }
    }

    private static boolean blocked(Player player, BlockPos horizontalPos) {
        AABB box = player.getBoundingBox();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int y = Mth.floor(box.minY); y < Mth.ceil(box.maxY); y++) {
            pos.set(horizontalPos.getX(), y, horizontalPos.getZ());
            if (player.level().getBlockState(pos).isSuffocating(player.level(), pos)) {
                return true;
            }
        }
        return false;
    }
}
