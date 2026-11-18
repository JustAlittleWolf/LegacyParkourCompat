package me.wolfii.legacyparkourcompat.change.v1_8_9.piston;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.PistonStairPushHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.AABB;

import java.util.OptionalDouble;

/**
 * 1.8.9 Ornithe Feather: MovingBlockEntity.tick and moveEntities query the
 * straight stair's full response AABB at the second extension step and pass
 * the float progress difference plus 0.0625F to Entity.move. MovingBlock and
 * StairsBlock leave ordinary collision behind the player on the clear eastward
 * route. In 1.9 Ornithe Feather, MovingBlockEntity.moveEntities instead uses
 * swept outline clearance and adds 0.01.
 *
 * <p>26.2 unobfuscated: PistonMovingBlockEntity.moveCollidedEntities collects
 * the player from the swept bounds of the entire stair shape. With the lower
 * slab and the upper step both missed, the existing response hook opens the
 * per-box gate and supplies the final distance to moveEntityByPiston.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class CobblestoneStairPush implements PistonStairPushHook {
    @Override
    public String variant() {
        return "minecraft:cobblestone_stairs";
    }

    @Override
    public OptionalDouble requestedDistance(PistonMovingBlockEntity mover, BlockPos pos, float progress,
                                             Entity recipient, Direction movementDirection) {
        float lastProgress = mover.getProgress(1.0F);
        if (!mover.isExtending() || mover.isSourcePiston() || movementDirection != Direction.EAST
            || progress != 1.0F || lastProgress != 0.5F
            || !(recipient instanceof LocalPlayer player) || !OrdinaryPlayerContext.matches(player)
            || player.getPose() != Pose.STANDING || player.isShiftKeyDown() || player.noPhysics
            || player.getAbilities().flying) {
            return OptionalDouble.empty();
        }

        var dimensions = player.getDimensions(Pose.STANDING);
        var state = mover.getMovedState();
        if (dimensions.width() != 0.6F || dimensions.height() != 1.8F
            || state.getValue(StairBlock.FACING) != Direction.NORTH
            || state.getValue(StairBlock.HALF) != Half.BOTTOM
            || state.getValue(StairBlock.SHAPE) != StairsShape.STRAIGHT
            || state.getValue(StairBlock.WATERLOGGED)) {
            return OptionalDouble.empty();
        }

        AABB box = player.getBoundingBox();
        // Stay above the lower slab and south of the upper step, in the
        // supported corner reached by the native broad sweep.
        if (box.minX < pos.getX() + 0.5 || box.minY != pos.getY() + 0.5
            || box.minZ < pos.getZ() + 0.5 || !new AABB(pos).intersects(box)) {
            return OptionalDouble.empty();
        }

        float distance = (progress - lastProgress) + 0.0625F;
        return player.level().noCollision(player, box.expandTowards(distance, 0.0, 0.0))
            ? OptionalDouble.of(distance) : OptionalDouble.empty();
    }
}
